package com.wanmi.sbc.bargainGoods.service;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoAndOtherByIdResponse;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.service.detail.GoodsDetailInterface;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsQueryRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className BargainGoodsResponse
 * @date 2022/8/18 18:05
 */
@Service
public class BargainGoodsInfoDetailService implements GoodsDetailInterface<BargainGoodsVO> {

    @Autowired CommonUtil commonUtil;

    @Autowired NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired private BargainGoodsQueryProvider bargainGoodsQueryProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private SystemConfigQueryProvider systemConfigQueryProvider;

    @Override
    public BargainGoodsVO getData(String bargainGoodsId) {
        String customerId = commonUtil.getOperatorId();
        //1. 查询砍价活动商品信息和用户发起的砍价记录
        BargainGoodsQueryRequest idReq = new BargainGoodsQueryRequest();
        idReq.setBargainGoodsId(Long.valueOf(bargainGoodsId));
        if (StringUtils.isNotEmpty(customerId)) {
            idReq.setUserId(customerId);
        }
        BargainGoodsVO bargainGoodsVO = bargainGoodsQueryProvider.getById(idReq).getContext();

        // 处理商品基本信息
        //2.0 查询关联的SKU
        GoodsInfoAndOtherByIdResponse goodsInfoByIdResponse =
                goodsInfoQueryProvider
                        .getSkuAndOtherInfoById(
                                GoodsInfoByIdRequest.builder()
                                        .goodsInfoId(bargainGoodsVO.getGoodsInfoId())
                                        .build())
                        .getContext();
        if (Objects.isNull(goodsInfoByIdResponse)
                || Objects.isNull(goodsInfoByIdResponse.getGoodsInfo())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        bargainGoodsVO.setGoodsInfoVO(goodsInfoByIdResponse.getGoodsInfo());
        GoodsInfoVO goodsInfo = bargainGoodsVO.getGoodsInfoVO();
        //2.1 根据商品的可售处理活动状态
        if (Objects.equals(Constants.no, goodsInfo.getVendibility(Boolean.TRUE))) {
            bargainGoodsVO.setGoodsStatus(DeleteFlag.NO);
        }

        //2.2 对每个SKU填充规格和规格值关系
        List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRels =
                goodsInfoByIdResponse.getSpecDetailRelVOList();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(goodsInfoSpecDetailRels)) {
            goodsInfo.setMockSpecIds(
                    goodsInfoSpecDetailRels.stream()
                            .map(GoodsInfoSpecDetailRelVO::getSpecId)
                            .collect(Collectors.toList()));
            goodsInfo.setMockSpecDetailIds(
                    goodsInfoSpecDetailRels.stream()
                            .map(GoodsInfoSpecDetailRelVO::getSpecDetailId)
                            .collect(Collectors.toList()));

            bargainGoodsVO.setSpecText(
                    StringUtils.join(
                            goodsInfoSpecDetailRels.stream()
                                    .map(GoodsInfoSpecDetailRelVO::getDetailName)
                                    .collect(Collectors.toList()),
                            " "));
        }
        //2.3 获取商品属性列表
        List<GoodsPropertyDetailRelVO> goodsPropertyDetailRelVOList =
                goodsInfoByIdResponse.getGoodsPropertyDetailRelVOList();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(
                goodsPropertyDetailRelVOList)) {
            bargainGoodsVO.setGoodsPropDetailRels(
                    KsBeanUtil.convertList(
                            goodsPropertyDetailRelVOList, GoodsPropDetailRelVO.class));
        }

        //2.4 获取商品图片信息
        List<GoodsImageVO> goodsImageVOList = goodsInfoByIdResponse.getGoodsImageVOList();
        if (CollectionUtils.isNotEmpty(goodsImageVOList)) {
            bargainGoodsVO.setImages(goodsImageVOList);
        }
        return bargainGoodsVO;
    }

    @Override
    public BargainGoodsVO setStock(BargainGoodsVO bargainGoodsVO, Long storeId) {
        return bargainGoodsVO;
    }

    @Override
    public BargainGoodsVO filter(BargainGoodsVO bargainGoodsVO, PlatformAddress address) {
        return bargainGoodsVO;
    }

    @Override
    public BargainGoodsVO setMarketing(BargainGoodsVO bargainGoodsVO, Long storeId) {
        // 查询优惠券叠加开关是否打开
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
        configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        SystemConfigTypeResponse response =
                systemConfigQueryProvider
                        .findByConfigTypeAndDelFlag(configQueryRequest)
                        .getContext();
        if (Objects.isNull(response.getConfig())
                || Objects.equals(DefaultFlag.NO.toValue(), response.getConfig().getStatus())) {
            return bargainGoodsVO;
        }

        //验证活动对应商品是否有效
        if (Objects.isNull(bargainGoodsVO.getGoodsInfoVO())
                || Objects.equals(DeleteFlag.YES, bargainGoodsVO.getGoodsInfoVO().getDelFlag())) {
            return bargainGoodsVO;
        }

        // 查询优惠券
        GoodsInfoPluginRequest goodsInfoPluginRequest =
                GoodsInfoPluginRequest.builder()
                        .goodsInfoPluginRequest(
                                KsBeanUtil.convert(
                                        bargainGoodsVO.getGoodsInfoVO(), GoodsInfoSimpleVO.class))
                        .storeId(bargainGoodsVO.getStoreId())
                        .build();
        goodsInfoPluginRequest.setTerminalSource(commonUtil.getTerminal().name());
        goodsInfoPluginRequest.setMarketingPluginType(MarketingPluginType.COUPON);
        goodsInfoPluginRequest.setHandlePosit(Boolean.FALSE);
        if (StringUtils.isNotBlank(commonUtil.getOperatorId())) {
            goodsInfoPluginRequest.setCustomerId(commonUtil.getOperatorId());
        }

        GoodsInfoDetailPluginResponse pluginResponse =
                newMarketingPluginProvider
                        .goodsInfoDetailPlugin(goodsInfoPluginRequest)
                        .getContext();
        if (pluginResponse != null) {
            bargainGoodsVO.setMarketingLabels(pluginResponse.getMarketingLabels());
        }
        return bargainGoodsVO;
    }

    @Override
    public BargainGoodsVO afterProcess(BargainGoodsVO bargainGoodsVO, Long storeId) {
        GoodsInfoVO goodsInfo = bargainGoodsVO.getGoodsInfoVO();
        // 处理商品可售性
        if (Objects.equals(DeleteFlag.NO, goodsInfo.getVendibility(Boolean.TRUE))) {
            bargainGoodsVO.setGoodsStatus(DeleteFlag.NO);
        }
        //处理商品状态
        if (Objects.equals(DeleteFlag.YES, goodsInfo.getDelFlag())
                || AddedFlag.NO.toValue() == goodsInfo.getAddedFlag()
                || !Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus())) {
            bargainGoodsVO.setGoodsStatus(DeleteFlag.NO);
        }
        return bargainGoodsVO;
    }
}
