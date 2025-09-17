package com.wanmi.sbc.marketing.newplugin.impl;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.FullBuyType;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginBaseService;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className MarketingNewComerPlugin
 * @description 新人专享券
 * @date 2022/8/22 11:10 AM
 **/
@Slf4j
@MarketingPluginService(type = MarketingPluginType.NEW_COMER_COUPON)
public class MarketingNewComerPlugin implements MarketingPluginInterface {
    @Autowired
    private MarketingPluginBaseService marketingPluginBaseService;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
        log.debug(" MarketingNewComerPlugin goodsDetail process");
        if (request.getCustomerId() != null) {
            Map<String, List<CouponInfoVO>> map = marketingPluginBaseService.getNewComerCoupon(
                    Collections.singletonList(request.getGoodsInfoPluginRequest()),
                    marketingPluginBaseService.getCustomerNoThirdImgById(request.getCustomerId()));
            GoodsInfoDetailPluginResponse response = MarketingContext.getResponse();
            GoodsInfoDetailPluginResponse newResponse = KsBeanUtil.convert(response, GoodsInfoDetailPluginResponse.class);
            List<CouponInfoVO> couponInfoVOS = map.get(request.getGoodsInfoPluginRequest().getGoodsInfoId());
            if (CollectionUtils.isNotEmpty(couponInfoVOS) && newResponse != null) {
                // 商品生效的会员价
                BigDecimal marketPrice = request.getGoodsInfoPluginRequest().getMarketPrice();
                List<MarketingPluginSimpleLabelVO> labels = KsBeanUtil.convert(newResponse.getMarketingLabels(), MarketingPluginSimpleLabelVO.class);
                BigDecimal customerPluginPrice = getCustomerPluginPrice(marketPrice, labels);
                //根据生效价计算新人券后价
                BigDecimal newcomerPrice = getNewcomerPrice(customerPluginPrice, couponInfoVOS);

                MarketingPluginLabelVO label = new MarketingPluginLabelVO();
                label.setMarketingType(MarketingPluginType.NEW_COMER_COUPON.getId());
                label.setMarketingDesc("新人券后价");
                label.setPluginPrice(newcomerPrice);
                label.setCanfetchFlag(marketingPluginBaseService.checkNewComerCanFetch(request.getCustomerId()));
                List<MarketingPluginLabelVO> labelList = new ArrayList();
                labelList.add(label);
                newResponse.setMarketingLabels(labelList);
                return newResponse;
            }
        }
        return null;
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {
        if (request.getCustomerId() != null) {
            Map<String, List<CouponInfoVO>> newComerMap = MarketingContext.getBaseRequest().getNewComerMap();
            List<MarketingPluginSimpleLabelVO> labelVOList = MarketingContext.getResponse();
            BigDecimal marketPrice = request.getGoodsInfoPluginRequest().getMarketPrice();
            List<CouponInfoVO> couponInfoVOS = newComerMap.get(request.getGoodsInfoPluginRequest().getGoodsInfoId());
            if (CollectionUtils.isNotEmpty(couponInfoVOS)) {

                BigDecimal customerPluginPrice = getCustomerPluginPrice(marketPrice, labelVOList);
                BigDecimal newcomerPrice = getNewcomerPrice(customerPluginPrice, couponInfoVOS);

                MarketingPluginLabelVO label = new MarketingPluginLabelVO();
                label.setMarketingType(MarketingPluginType.NEW_COMER_COUPON.getId());
                label.setMarketingDesc("新人券后价");
                label.setPluginPrice(newcomerPrice);
                return label;
            }
        }
        return null;
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        return null;
    }

    /**
     * 计算新人券后价
     * @return
     */
    private BigDecimal getNewcomerPrice(BigDecimal pluginPrice, List<CouponInfoVO> coupons) {
        BigDecimal finalPluginPrice = pluginPrice;
        List<CouponInfoVO> canUseCoupons = coupons.stream().filter(couponInfoVO -> {
            if (FullBuyType.FULL_MONEY == couponInfoVO.getFullBuyType()
                    && finalPluginPrice.compareTo(couponInfoVO.getFullBuyPrice()) < NumberUtils.INTEGER_ZERO) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(canUseCoupons)) {
            Optional<CouponInfoVO> optional = canUseCoupons.stream()
                    .max(Comparator.comparing(CouponInfoVO::getDenomination));
            if (optional.isPresent()) {
                BigDecimal denomination = optional.get().getDenomination();
                if (pluginPrice.compareTo(denomination) > NumberUtils.INTEGER_ZERO) {
                    pluginPrice = pluginPrice.subtract(denomination);
                } else {
                    pluginPrice = BigDecimal.ZERO;
                }
            }

        }
        return pluginPrice;
    }

    /**
     * 获取商品生效价
     * @param marketingLabels
     * @return
     */
    private BigDecimal getCustomerPluginPrice(BigDecimal marketingPrice, List<MarketingPluginSimpleLabelVO> marketingLabels) {
        PayingMemberLevelVO payingMemberLevel = MarketingContext.getBaseRequest().getPayingMemberLevel();
        if (CollectionUtils.isEmpty(marketingLabels)) {
            return marketingPrice;
        }

        //客户独立设价
        Optional<MarketingPluginSimpleLabelVO> customerPriceOption = marketingLabels.stream()
                .filter(label -> MarketingPluginType.CUSTOMER_PRICE.getId() == label.getMarketingType())
                .findFirst();
        if (customerPriceOption.isPresent()) {
            return customerPriceOption.get().getPluginPrice();
        }

        if (payingMemberLevel != null && payingMemberLevel.getLevelId() != null) {
            Optional<MarketingPluginSimpleLabelVO> optional = marketingLabels.stream().filter(label -> MarketingPluginType.PAYING_MEMBER.getId() == label.getMarketingType()
                    && Boolean.TRUE.equals(payingMemberLevel.getOwnFlag())).findFirst();
            if (optional.isPresent()) {
                return optional.get().getPluginPrice();
            }
        }

        //客户等级价
        Optional<MarketingPluginSimpleLabelVO> customerLevelOption = marketingLabels.stream()
                .filter(label -> MarketingPluginType.CUSTOMER_LEVEL.getId() == label.getMarketingType())
                .findFirst();
        if (customerLevelOption.isPresent()) {
            return customerLevelOption.get().getPluginPrice();
        }
        return marketingPrice;
    }



}
