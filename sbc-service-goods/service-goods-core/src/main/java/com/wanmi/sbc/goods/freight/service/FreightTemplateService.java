package com.wanmi.sbc.goods.freight.service;

import com.wanmi.sbc.common.base.VASEntity;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceByCustomerIdRequest;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import com.wanmi.sbc.goods.bean.vo.FreightGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.freight.vo.FreightPackageGoodsPriceVO;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.price.service.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingLevelPluginProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingLevelGoodsListFilterRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*
 * @description   运费模板处理接口
 * @author  wur
 * @date: 2022/7/7 11:17
 **/
@Service
public class FreightTemplateService {

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private GoodsRepository goodsRepository;

    @Autowired private MarketingLevelPluginProvider marketingLevelPluginProvider;

    @Autowired private RedisUtil redisService;

    @Autowired private GoodsIntervalPriceService goodsIntervalPriceService;

    /**
     * 封装用户的商品价格  订货区间价   用户等级价   付费会员价  企业会员价
     * @param customer
     * @return
     */
    public FreightPackageGoodsPriceVO packageGoodsPrice(List<FreightGoodsInfoVO> freightGoodsInfoVOList, CustomerDTO customer) {
        //查询商品信息
        List<String> skuIdList = freightGoodsInfoVOList.stream().map(FreightGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfo> skuList = goodsInfoRepository.findByGoodsInfoIds(skuIdList);
        List<String> spuIdList = skuList.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList());
        Map<String, Goods> spuMap = goodsRepository.findAllByGoodsIdIn(spuIdList).stream().collect(Collectors.toMap(Goods :: getGoodsId,
                Function.identity(), (k1, k2) -> k1));
        List<GoodsInfoVO> goodsInfoList = KsBeanUtil.convert(skuList, GoodsInfoVO.class);
        Map<String, FreightGoodsInfoVO> freightGoodsInfoVOMap = freightGoodsInfoVOList.stream().collect(Collectors.toMap(FreightGoodsInfoVO :: getGoodsInfoId,
                Function.identity(), (k1, k2) -> k1));
        goodsInfoList.forEach(goodsInfo -> {
            Goods goods = spuMap.get(goodsInfo.getGoodsId());
            goodsInfo.setPriceType(goods.getPriceType());
            goodsInfo.setSalePrice(goodsInfo.getMarketPrice());
            goodsInfo.setBuyCount(freightGoodsInfoVOMap.get(goodsInfo.getGoodsInfoId()).getNum());
        });
        //计算区间价
        GoodsIntervalPriceByCustomerIdResponse intervalPriceResponse =
                goodsIntervalPriceService.putByCustomerId(
                        GoodsIntervalPriceByCustomerIdRequest.builder()
                                .goodsInfoDTOList(KsBeanUtil.convert(goodsInfoList, GoodsInfoDTO.class))
                                .customerId(customer != null ? customer.getCustomerId() : null).build()).getContext();
        List<GoodsIntervalPriceVO> goodsIntervalPrices = intervalPriceResponse.getGoodsIntervalPriceVOList();
        goodsInfoList = intervalPriceResponse.getGoodsInfoVOList();

        //计算级别价格
        goodsInfoList = marketingLevelPluginProvider.goodsListFilter(
                MarketingLevelGoodsListFilterRequest.builder()
                        .customerId(customer != null ? customer.getCustomerId() : null)
                        .goodsInfos(KsBeanUtil.convert(goodsInfoList, GoodsInfoDTO.class)).build())
                .getContext().getGoodsInfoVOList();

        Map<String, BigDecimal> skuSalePriceMap = new HashMap<>();
        goodsInfoList.forEach(goodsInfo -> {
            Goods goods = spuMap.get(goodsInfo.getGoodsId());
            //企业价
            Boolean IEPFlag = Boolean.FALSE;
            //判断当前会员是否是企业购会员，商品是否是企业购商品
            if (Objects.nonNull(customer) && customer.getEnterpriseCheckState() == EnterpriseCheckState.CHECKED
                    && Objects.nonNull(goodsInfo.getEnterPriseAuditState())
                    && goodsInfo.getEnterPriseAuditState() == EnterpriseAuditState.CHECKED
                    && Objects.nonNull(goodsInfo.getEnterPrisePrice())) {
                Map<String, Object> vasList = redisService.hgetAllObj(ConfigKey.VALUE_ADDED_SERVICES.toString());
                List<VASEntity> VASList = vasList.entrySet().stream().map(m -> {
                    VASEntity vasEntity = new VASEntity();
                    vasEntity.setServiceName(VASConstants.fromValue(m.getKey()));
                    vasEntity.setServiceStatus(StringUtils.equals(VASStatus.ENABLE.toValue(), m.getValue().toString()));
                    return vasEntity;
                }).collect(Collectors.toList());
                VASEntity vasEntity = VASList.stream()
                        .filter(f -> StringUtils.equals(f.getServiceName().toValue(), VASConstants.VAS_IEP_SETTING.toValue()) && f.isServiceStatus())
                        .findFirst().orElse(null);
                if (Objects.nonNull(vasEntity) && vasEntity.isServiceStatus()) {
                    IEPFlag = Boolean.TRUE;
                }
            }
            if (IEPFlag) {
                skuSalePriceMap.put(goodsInfo.getGoodsInfoId(), goodsInfo.getEnterPrisePrice());
            } else if (goods.getPriceType() == GoodsPriceType.STOCK.toValue()) {
                // 按区间设价，获取满足的多个等级的区间价里的最大价格
                Optional<GoodsIntervalPriceVO> optional = goodsIntervalPrices.stream().filter(goodsIntervalPrice
                        -> goodsIntervalPrice.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId()))
                        .filter(goodsIntervalPrice -> goodsInfo.getBuyCount().compareTo(goodsIntervalPrice.getCount()) >= 0).max(Comparator.comparingLong(GoodsIntervalPriceVO::getCount));
                if (optional.isPresent()) {
                    skuSalePriceMap.put(goodsInfo.getGoodsInfoId(), optional.get().getPrice());
                } else {
                    skuSalePriceMap.put(goodsInfo.getGoodsInfoId(), BigDecimal.ZERO);
                }
            } else if (Boolean.TRUE.equals(goodsInfo.getPayMemberOwnFlag()) && SaleType.RETAIL.toValue() == goods.getSaleType()) {
                skuSalePriceMap.put(goodsInfo.getGoodsInfoId(), goodsInfo.getPayMemberPrice());
            } else if (goods.getPriceType() == GoodsPriceType.CUSTOMER.toValue()) {
                // 按级别设价，获取级别价
                skuSalePriceMap.put(goodsInfo.getGoodsInfoId(), goodsInfo.getSalePrice());
            } else {
                skuSalePriceMap.put(goodsInfo.getGoodsInfoId(), goodsInfo.getMarketPrice());
            }
        });

        // 计算商品总额
        BigDecimal totalAmount = freightGoodsInfoVOList.stream()
                .map(goodsInfo -> skuSalePriceMap.getOrDefault(goodsInfo.getGoodsInfoId(), BigDecimal.ZERO).multiply(BigDecimal.valueOf(goodsInfo.getNum())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return FreightPackageGoodsPriceVO.builder().totalAmount(totalAmount).goodsInfoList(goodsInfoList).build();
    }

    /**
     * @description   收货地址匹配
     * @author  wur
     * @date: 2022/7/7 11:29
     * @param areaStr
     * @param provId
     * @param cityId
     * @return
     **/
    public boolean matchArea(String areaStr, String provId, String cityId) {
        String[] arr = areaStr.split(",");
        return Arrays.stream(arr).anyMatch(area -> area.equals(provId))
                || Arrays.stream(arr).anyMatch(area -> area.equals(cityId));
    }

}
