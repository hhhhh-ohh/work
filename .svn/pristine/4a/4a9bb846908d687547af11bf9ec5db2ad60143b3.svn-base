package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.account.bean.enums.*;
import com.wanmi.sbc.account.bean.vo.SettleGoodVO;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 结算明细前台和导出公用展示实体
 */
@Schema
@Data
public class SettlementDetailViewVO extends BasicResponse {

    /**
     * 序号
     */
    @Schema(description = "序号")
    private Long index;

    /**
     * 订单创建时间
     */
    @Schema(description = "订单创建时间")
    private String tradeCreateTime;

    /**
     * 订单入账时间
     */
    @Schema(description = "订单入账时间")
    private String finalTime;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String tradeCode;

    /**
     * 订单类型（普通订单  积分订单）
     */
    @Schema(description = "订单类型")
    private String orderType;

    /**
     * 订单类型
     */
    @Schema(description = "订单类型", contentSchema = com.wanmi.sbc.account.bean.enums.TradeType.class)
    private String tradeType;

    /**
     * 运费
     */
    @Schema(description = "运费")
    private String deliveryPrice;

    /**
     * 收款方
     */
    @Schema(description = "收款方", contentSchema = com.wanmi.sbc.account.bean.enums.GatherType.class)
    private String gatherType;

    /**
     * 退货金额
     */
    @Schema(description = "退货金额")
    private String returnPrice;

    /**
     * 退单改价差额
     */
    @Schema(description = "退单改价差额")
    private BigDecimal returnSpecialPrice;

    /**
     * 店铺应收金额
     */
    @Schema(description = "店铺应收金额")
    private BigDecimal storePrice;

    /**
     * 供货总额
     */
    @Schema(description = "供货总额")
    private BigDecimal providerPrice;

    /**
     * 第三方供应商运费
     */
    @Schema(description = "第三方供应商运费")
    private BigDecimal thirdPlatFormFreight = BigDecimal.ZERO.setScale(2);

    /**
     * 积分商品结算价
     */
    @Schema(description = "积分商品结算价")
    private BigDecimal pointsSettlementPrice;

    /**
     * 商品集合
     */
    @Schema(description = "商品集合")
    private List<SettlementDetailGoodsViewVO> goodsViewList;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private String payWayValue;

    /**
     * 运费
     */
    @Schema(description = "运费")
    private BigDecimal fee;

    /**
     * 商家结算-供应商退单改价差额
     */
    @Schema(description = "商家结算-供应商退单改价差额")
    private BigDecimal providerReturnSpecialPrice;

    /**
     * 运费优惠金额
     */
    @Schema(description = "运费优惠金额")
    private String freightCouponPrice;

    /**
     * 礼品卡抵扣金额
     */
    @Schema(description = "礼品卡抵扣金额")
    private String totalPickupGiftCardPrice;

    /**
     * 礼品卡类型
     */
    @Schema(description = "礼品卡类型")
    private GiftCardType giftCardType;

    /**
     * 抵扣方式
     */
    @Schema(description = "抵扣方式")
    private String orderDeductionTypeValue;


    /**
     * 组装前台展示的实体
     *
     * @param settlementDetails
     * @return
     */
    public static List<SettlementDetailViewVO> renderSettlementDetailForView(List<SettlementDetailVO> settlementDetails,
                                                                           boolean isExcel) {
        if (settlementDetails == null || settlementDetails.isEmpty()) {
            return new ArrayList<>();
        }
        AtomicInteger count = new AtomicInteger();
        return settlementDetails.stream().map(detail -> {
            SettlementDetailViewVO view = new SettlementDetailViewVO();
            BigDecimal returnPrice = detail.getSettleTrade().getReturnPrice();
            BigDecimal deliveryPrice = detail.getSettleTrade().getDeliveryPrice();
            BigDecimal freightCouponPrice = detail.getSettleTrade().getFreightCouponPrice();
            BigDecimal giftCardPrice  = detail.getSettleTrade().getGiftCardPrice();
            GiftCardType giftCardType1  = detail.getSettleTrade().getGiftCardType();
            //组装前台显示的实体，excel导出和页面展示
            view.setIndex((long) count.incrementAndGet());
            view.setTradeCreateTime(DateUtil.format(detail.getSettleTrade().getTradeCreateTime(), DateUtil.FMT_TIME_1));
            LocalDateTime finalTime = detail.getSettleTrade().getFinalTime();
            view.setFinalTime(Objects.nonNull(finalTime) ? DateUtil.format(finalTime, DateUtil.FMT_TIME_1) : "");
            view.setTradeCode(detail.getSettleTrade().getTradeCode());
            view.setTradeType(detail.getSettleTrade().getTradeType() == TradeType.NORMAL_TRADE ? "普通" : "促销");
            view.setOrderType(detail.getSettleTrade().getOrderType() == OrderType.POINTS_ORDER ? "积分订单" : "普通订单");
            view.setDeliveryPrice(deliveryPrice != null ? deliveryPrice.toString() : "-");
            view.setGatherType(detail.getSettleTrade().getGatherType() == GatherType.PLATFORM ? "平台" : "供应商");
            view.setFreightCouponPrice(Objects.nonNull(freightCouponPrice) ? freightCouponPrice.toString(): "-");
            view.setTotalPickupGiftCardPrice(Objects.nonNull(giftCardPrice) ? giftCardPrice.toString(): "-");
            view.setGiftCardType(giftCardType1);


            if(detail.getSettleGoodList().stream().map(SettleGoodVO::getReturnNum).max(Long::compare).get() > 0){
                view.setReturnPrice(returnPrice.setScale(2, RoundingMode.HALF_UP).toString());
            }else{
                view.setReturnPrice("-");
            }

            view.setGoodsViewList(detail.getSettleGoodList().stream().map(goods -> {
                SettlementDetailGoodsViewVO goodsView = new SettlementDetailGoodsViewVO();
                goodsView.setGoodsName(goods.getGoodsName());
                goodsView.setSpecial(detail.isSpecial());
                if (goods.getSkuNo() != null && !"".equals(goods.getSkuNo())) {
                    goodsView.setSkuNo(goods.getSkuNo());
                    if (isExcel) {
                        goodsView.setGoodsName(goods.getSkuNo().concat(" ").concat(goodsView.getGoodsName()));
                    }
                }
                if (goods.getSpecDetails() != null && !"".equals(goods.getSpecDetails())) {
                    goodsView.setSpecDetails(goods.getSpecDetails());
                    if (isExcel) {
                        goodsView.setGoodsName(goodsView.getGoodsName().concat(" ").concat(goods.getSpecDetails()));
                    }
                }
                goodsView.setGoodsPrice(goods.getGoodsPrice());
                goodsView.setCateName(goods.getCateName());
                if(Objects.nonNull(goods.getCateRate())){
                    goodsView.setCateRate(goods.getCateRate().toString().concat("%"));
                    //因为解析的时候是先算订单所得佣金，再算退单所还佣金，floor保留2位，保持统一
                    //订单对应佣金
                    goodsView.setPlatformPrice(goods.getSplitPayPrice() != null ? // 现在按照实付金额算，不需要乘以数量
                            goods.getSplitPayPrice().multiply(goods.getCateRate()).divide(new BigDecimal(100)).setScale(2, RoundingMode.FLOOR)
                            : BigDecimal.ZERO);
                }else{
                    goodsView.setCateRate(BigDecimal.ZERO.toString().concat("%"));
                }

                goodsView.setNum(goods.getNum());

                //计算营销和特价各个优惠价
                if (detail.getSettleTrade().getOrderType() != OrderType.POINTS_ORDER) {
                    setSpecialOrMarketingPrice(goodsView, goods);
                }

                goodsView.setSplitPayPrice(goods.getSplitPayPrice() != null ? goods.getSplitPayPrice() : BigDecimal.ZERO);
                if (GiftCardType.PICKUP_CARD.equals(giftCardType1)){
                    goodsView.setSplitPayPrice(BigDecimal.ZERO);
                }

                if (goods.getReturnStatus() != null) {
                    goodsView.setReturnStatus(goods.getReturnStatus() == ReturnStatus.RETURNED ? "已退" : "进行中");
                } else {
                    goodsView.setReturnStatus("-");
                }

                goodsView.setReturnNum(goods.getReturnNum() != null && goods.getReturnNum() != 0L ? goods
                        .getReturnNum().toString() : "-");

                if (goods.getReturnNum() != null && goods.getReturnNum() != 0L && goods.getShouldReturnPrice() != null){
                    goodsView.setShouldReturnPrice(goods.getShouldReturnPrice().toString());
                }else{
                    goodsView.setShouldReturnPrice("-");
                }
                goodsView.setPointPrice(goods.getPointPrice());
                goodsView.setPoints(Objects.isNull(goods.getPoints())?0L:goods.getPoints());
                goodsView.setCommissionRate(goods.getCommissionRate().setScale(2, RoundingMode.HALF_UP));
                goodsView.setCommission(goods.getCommission());
                goodsView.setGiftCardPrice(Objects.nonNull(goods.getGiftCardPrice())?goods.getGiftCardPrice() : BigDecimal.ZERO);
                goodsView.setGiftCardType(goods.getGiftCardType());
                if(null != goods.getSupplyPrice()){
                    goodsView.setSupplyPrice(goods.getSupplyPrice());
                    goodsView.setProviderPrice(goods.getSupplyPrice().multiply(new BigDecimal(goods.getNum())));
                }

                if (GiftCardType.PICKUP_CARD.equals(giftCardType1)){
                    goodsView.setPointPrice(BigDecimal.ZERO);
                    goodsView.setPoints(0L);
                }
                goodsView.setCommunityCommission(goods.getCommunityCommission());
                goodsView.setCommunityCommissionRate(goods.getCommunityCommissionRate());
                return goodsView;
            }).collect(Collectors.toList()));

            // 店铺应收金额
            BigDecimal storePrice = detail.getSettleTrade().getStorePrice();
            view.setStorePrice(Objects.nonNull(storePrice) ? storePrice : BigDecimal.ZERO);
            // 退单改价差额
            BigDecimal returnSpecialPrice = detail.getSettleTrade().getReturnSpecialPrice();
            view.setReturnSpecialPrice(Objects.nonNull(returnSpecialPrice) ? returnSpecialPrice : BigDecimal.ZERO);
            // 商家结算- 供应商退单改价差额
            BigDecimal providerReturnSpecialPrice = detail.getSettleTrade().getProviderReturnSpecialPrice();
            view.setProviderReturnSpecialPrice(Objects.nonNull(providerReturnSpecialPrice) ? providerReturnSpecialPrice : BigDecimal.ZERO);

            //供货运费
            BigDecimal thirdPlatFormFreight = detail.getSettleTrade().getThirdPlatFormFreight();
            view.setThirdPlatFormFreight(thirdPlatFormFreight);

            //供货总额
            BigDecimal providerPrice =  view.getGoodsViewList().stream().filter(goodsView->null!=goodsView.getProviderPrice()).map(SettlementDetailGoodsViewVO::getProviderPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
            //供货总额=供货金额+供货运费
            if(thirdPlatFormFreight != null && thirdPlatFormFreight.compareTo(BigDecimal.ZERO) > 0){
                providerPrice = providerPrice.add(thirdPlatFormFreight);
            }
            view.setProviderPrice(providerPrice);

            PayWay payWay = detail.getSettleTrade().getPayWay();
            Long points = detail.getSettleTrade().getPoints();
            StringBuilder sb = new StringBuilder();
            BigDecimal splitPayPrice = BigDecimal.ZERO;

            //商品实付金额
            if(CollectionUtils.isNotEmpty(view.getGoodsViewList())){
                splitPayPrice = view.getGoodsViewList().stream()
                        .map(SettlementDetailGoodsViewVO::getSplitPayPrice).reduce(BigDecimal.ZERO, (total, price) -> total.add(price));
            }
            if(Objects.nonNull(payWay) && splitPayPrice.compareTo(BigDecimal.ZERO) > 0){
                if(Objects.nonNull(points) && points > 0 && !payWay.equals(PayWay.POINT)){
                    view.setOrderDeductionTypeValue("积分");
                }
                sb.append(payWay.getDesc());
            }else{
                if(Objects.nonNull(points) && points > 0){
                    sb.append("积分");
                }else{
                    sb.append("-");
                }
            }
            view.setPayWayValue(sb.toString());
            if (GiftCardType.PICKUP_CARD.equals(giftCardType1)){
                view.setPayWayValue("-");
            }
            view.setFee(detail.getFee());

            // 处理抵扣方式
            boolean usePointsFlag = Objects.nonNull(points) && points > 0;
            boolean useGiftCardPriceFlag = Objects.nonNull(giftCardPrice) && giftCardPrice.compareTo(BigDecimal.ZERO) > 0;
            if (usePointsFlag && useGiftCardPriceFlag) {
                view.setOrderDeductionTypeValue("积分/礼品卡-现金卡");
                if(GiftCardType.PICKUP_CARD.equals(giftCardType1)){
                    view.setOrderDeductionTypeValue("礼品卡-提货卡");
                }
            } else if (usePointsFlag) {
                view.setOrderDeductionTypeValue("积分");
            } else if (useGiftCardPriceFlag) {
                view.setOrderDeductionTypeValue("礼品卡-现金卡");
                if(GiftCardType.PICKUP_CARD.equals(giftCardType1)){
                    view.setOrderDeductionTypeValue("礼品卡-提货卡");
                }
            } else {
                view.setOrderDeductionTypeValue("-");
            }

            return view;
        }).collect(Collectors.toList());
    }

    /**
     * 按顺序执行，运算营销的优惠和特价
     * 1。满减/满折
     * 2。特价
     *
     * @param goodsView
     * @param settleGood
     */
    private static void setSpecialOrMarketingPrice(SettlementDetailGoodsViewVO goodsView, SettleGoodVO settleGood) {
        BigDecimal priceForSettle = settleGood.getGoodsPrice().multiply(new BigDecimal(settleGood.getNum()));
        //按顺序获取优惠价格
        //1.满折或者满减
        if (CollectionUtils.isNotEmpty(settleGood.getMarketingSettlements())) {
            for (SettleGoodVO.MarketingSettlement marketingSettlement : settleGood.getMarketingSettlements()) {
                //如果存在满减或者满折营销活动，计算满折/满减优惠价格
                if (marketingSettlement.getMarketingType() == SettleMarketingType.DISCOUNT) {
                    goodsView.setDiscountPrice(priceForSettle.subtract(marketingSettlement.getSplitPrice()).toString());
                    priceForSettle = marketingSettlement.getSplitPrice();
                }

                if (marketingSettlement.getMarketingType() == SettleMarketingType.REDUCTION) {
                    goodsView.setReductionPrice(priceForSettle.subtract(marketingSettlement.getSplitPrice()).toString());
                    priceForSettle = marketingSettlement.getSplitPrice();
                }
            }
        }
        //2.优惠券
        if (CollectionUtils.isNotEmpty(settleGood.getCouponSettlements())){
            //先计算店铺券
            Optional<SettleGoodVO.CouponSettlement> couponSettle = settleGood.getCouponSettlements().stream()
                    .filter(item -> item.getCouponType() == SettleCouponType.STORE_VOUCHERS).findFirst();
            if (couponSettle.isPresent()){
                goodsView.setStoreCouponPrice(couponSettle.get().getReducePrice().toString());
                priceForSettle = couponSettle.get().getSplitPrice();
            }
            //计算平台券
            couponSettle = settleGood.getCouponSettlements().stream()
                    .filter(item -> item.getCouponType() == SettleCouponType.GENERAL_VOUCHERS).findFirst();
            if (couponSettle.isPresent()){
                goodsView.setCommonCouponPrice(couponSettle.get().getReducePrice());
                priceForSettle = couponSettle.get().getSplitPrice();
            }
        }

        // 3.积分抵扣
        if (Objects.nonNull(settleGood.getPointPrice())) {
            goodsView.setPointPrice(settleGood.getPointPrice());
            priceForSettle = priceForSettle.subtract(settleGood.getPointPrice());
        }

        // 4.特价
        BigDecimal specialPrice = priceForSettle.subtract(settleGood.getSplitPayPrice() != null ? settleGood.getSplitPayPrice() : BigDecimal.ZERO);
        switch (specialPrice.compareTo(BigDecimal.ZERO)) {
            //大于0 -- 原价比特价高
            case 1:
                goodsView.setSpecialPrice("-".concat(specialPrice.toString()));
                break;
            //等于0 -- 特价和原价相同
            case 0:
                goodsView.setSpecialPrice(specialPrice.toString());
                break;
            //小于0 -- 原价比特价低
            case -1:
                goodsView.setSpecialPrice("+".concat(specialPrice.abs().toString()));
                break;
            default:
                goodsView.setSpecialPrice(specialPrice.toString());
                break;
        }
    }

}
