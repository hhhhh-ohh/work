package com.wanmi.sbc.goods.provider.impl.goodsaudit;

import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.api.request.goods.GoodsAuditViewByIdRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsAuditViewByIdResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByConditionResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditSaveVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.brand.service.GoodsBrandService;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.common.SystemPointsConfigService;
import com.wanmi.sbc.goods.goodsaudit.response.GoodsAuditEditResponse;
import com.wanmi.sbc.goods.goodsaudit.response.GoodsAuditQueryResponse;
import com.wanmi.sbc.goods.goodslabel.service.GoodsLabelService;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.storecate.entity.StoreCateBase;
import com.wanmi.sbc.goods.storecate.service.StoreCateService;
import com.wanmi.sbc.goods.util.mapper.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditPageRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditQueryRequest;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditPageResponse;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditListResponse;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditByIdRequest;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditByIdResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsAuditVO;
import com.wanmi.sbc.goods.goodsaudit.service.GoodsAuditService;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>商品审核查询服务接口实现</p>
 *
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@RestController
@Validated
public class GoodsAuditQueryController implements GoodsAuditQueryProvider {

    @Resource
    private GoodsAuditService goodsAuditService;

    @Resource
    private SystemPointsConfigService systemPointsConfigService;

    @Resource
    private GoodsAuditMapper goodsAuditMapper;

    @Resource
    private GoodsCateService goodsCateService;

    @Resource
    private GoodsBrandService goodsBrandService;

    @Resource
    private StoreCateService storeCateService;

    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private GoodsLabelService goodsLabelService;

    @Resource
    private GoodsService goodsService;

    @Override
    public BaseResponse<GoodsAuditPageResponse> page(@RequestBody @Valid GoodsAuditPageRequest request) {
        GoodsAuditQueryRequest req = KsBeanUtil.convert(request, GoodsAuditQueryRequest.class);
        GoodsAuditQueryResponse queryResponse = goodsAuditService.page(req);
        Page<GoodsAuditSaveVO> goodsAuditPage = queryResponse.getGoodsAuditPage();
        GoodsAuditPageResponse response = new GoodsAuditPageResponse();
        MicroServicePage<GoodsAuditVO> microServicePage = new MicroServicePage<>();
        if (Objects.nonNull(goodsAuditPage) && CollectionUtils.isNotEmpty(goodsAuditPage.getContent())) {
            microServicePage.setPageable(goodsAuditPage.getPageable());
            microServicePage.setTotal(goodsAuditPage.getTotalElements());
            List<GoodsAuditVO> goodsAuditVOList = goodsAuditMapper.goodsAuditSaveToVoList(goodsAuditPage.getContent());
            goodsAuditVOList.forEach(vo -> {
                //获取商品类目
                vo.setCateName(goodsCateService.findById(vo.getCateId()).getCateName());
                //获取商品品牌
                if(Objects.nonNull(vo.getBrandId())){
                    vo.setBrandName(goodsBrandService.findById(vo.getBrandId()).getBrandName());
                }
                //获取店铺分类
                vo.setStoreCateNames(storeCateService.findAllByStoreCateIdIn(vo.getStoreCateIds()).stream().map(StoreCateBase::getCateName).collect(Collectors.toList()));

            });
            if (Objects.equals(CheckStatus.NOT_PASS.toValue(),request.getAuditStatus())){
                //未审核Tab下校验是否可以编辑
                goodsAuditService.checkEdit(goodsAuditVOList);
            }
            microServicePage.setContent(goodsAuditVOList);
            //当不需示强制显示积分时，在设置未开启商品抵扣下清零buyPoint
            if (!Boolean.TRUE.equals(request.getShowPointFlag()) && CollectionUtils.isNotEmpty(microServicePage.getContent())) {
                long sum = microServicePage.getContent().stream().filter(g -> g.getBuyPoint() != null && g.getBuyPoint() > 0)
                        .mapToLong(GoodsAuditVO::getBuyPoint).sum();
                if (sum > 0 && (!systemPointsConfigService.isGoodsPoint())) {
                    microServicePage.getContent().forEach(g -> g.setBuyPoint(0L));
                }
            }
        }
        response.setGoodsAuditVOPage(microServicePage);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsAuditByIdResponse> getById(@RequestBody @Valid GoodsAuditByIdRequest goodsAuditByIdRequest) {
        GoodsAudit goodsAudit =
                goodsAuditService.getGoodsAuditById(goodsAuditByIdRequest.getGoodsId());
        return BaseResponse.success(new GoodsAuditByIdResponse(goodsAuditService.wrapperVo(goodsAudit)));
    }

    @Override
    public BaseResponse<GoodsAuditViewByIdResponse> getViewById(@RequestBody GoodsAuditViewByIdRequest request) {
        String goodsId = request.getGoodsId();
        Boolean isEditProviderGoods = request.getIsEditProviderGoods();
        GoodsAuditEditResponse goodsAuditEditResponse = goodsAuditService.findInfoById(goodsId, isEditProviderGoods);
        GoodsAuditViewByIdResponse goodsByIdResponse = KsBeanUtil.convert(goodsAuditEditResponse, GoodsAuditViewByIdResponse.class);
        GoodsAuditVO goodsAudit = goodsByIdResponse.getGoodsAudit();
        if (StringUtils.isNotBlank(goodsAudit.getProviderGoodsId()) && Objects.nonNull(goodsAudit.getProviderId())) {
            Goods providerGoods = goodsService.getGoodsById(goodsAudit.getProviderGoodsId());
            if (Objects.nonNull(providerGoods)) {
                goodsAudit.setProviderGoodsNo(providerGoods.getGoodsNo());
            }
            List<String> providerGoodsInfoIdList
                    = goodsByIdResponse.getGoodsInfos().stream().filter(v -> !ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                    .map(GoodsInfoVO::getProviderGoodsInfoId).collect(Collectors.toList());
            if (providerGoodsInfoIdList.size()>0) {
                List<GoodsInfo> providerGoodsInfoList = goodsInfoService.findByIds(providerGoodsInfoIdList);
                if (CollectionUtils.isNotEmpty(providerGoodsInfoList)) {
                    goodsByIdResponse.getGoodsInfos().forEach(goodsInfoVO -> {
                        GoodsInfo providerGoodsInfo = providerGoodsInfoList.stream()
                                .filter(v -> v.getGoodsInfoId().equals(goodsInfoVO.getProviderGoodsInfoId())).findFirst().orElse(null);
                        if (Objects.nonNull(providerGoodsInfo)) {
                            goodsInfoVO.setProviderGoodsInfoNo(providerGoodsInfo.getGoodsInfoNo());
                            goodsInfoVO.setStock(providerGoodsInfo.getStock());
                            //商家端下供应商商品未代销时自动同步供应商数据
                            if( Objects.nonNull(goodsInfoVO.getDelFlag())&& goodsInfoVO.getDelFlag().toValue() == 1){
                                goodsInfoVO.setGoodsInfoBarcode(providerGoodsInfo.getGoodsInfoBarcode());
                                goodsInfoVO.setGoodsInfoImg(providerGoodsInfo.getGoodsInfoImg());
                            }
                        }
                    });
                }
            }
        }
        //控制是否显示商品标签
        if (Boolean.TRUE.equals(request.getShowLabelFlag())) {
            goodsLabelService.fillGoodsLabel(Collections.singletonList(goodsAudit), request.getShowSiteLabelFlag());
        }

        //未开启商品抵扣时，清零buyPoint
        systemPointsConfigService.clearBuyPoinsForSkus(goodsByIdResponse.getGoodsInfos());
        return BaseResponse.success(goodsByIdResponse);
    }

    @Override
    public BaseResponse<GoodsAuditListResponse> getWaitCheckGoodsAudit(@RequestBody GoodsAuditQueryRequest request) {
        GoodsAuditListResponse response = goodsAuditService.getWaitCheckGoodsAudit(request);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsAuditListResponse> getByIds(@RequestBody GoodsAuditQueryRequest request) {
        GoodsAuditListResponse response = goodsAuditService.getByIds(request);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsAuditListResponse> getListByOldGoodsIds(@RequestBody GoodsAuditQueryRequest request) {
        GoodsAuditListResponse response = goodsAuditService.getListByOldGoodsIds(request.getGoodsIdList());

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsAuditListResponse> listByCondition(GoodsAuditQueryRequest request) {
        GoodsAuditQueryRequest queryRequest = KsBeanUtil.convert(request, GoodsAuditQueryRequest.class);
        List<GoodsAudit> list = goodsAuditService.findAll(queryRequest);

        if (CollectionUtils.isEmpty(list)) {
            return BaseResponse.success(new GoodsAuditListResponse(Collections.EMPTY_LIST));
        }
        List<GoodsAuditVO> goodsAuditVOList = KsBeanUtil.convert(list, GoodsAuditVO.class);
        return BaseResponse.success(new GoodsAuditListResponse(goodsAuditVOList));
    }

}

