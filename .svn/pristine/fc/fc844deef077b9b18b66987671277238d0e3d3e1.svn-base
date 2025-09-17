package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.account.bean.enums.*;
import com.wanmi.sbc.account.bean.vo.SettleGoodVO;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.util.DateUtil;

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

/** 结算明细前台和导出公用展示实体 */
@Schema
@Data
public class LakalaSettlementDetailViewVO extends BasicResponse {

    @Schema(description = "主键")
    private String id;

    /** 序号 */
    @Schema(description = "序号")
    private Long index;

    /** 订单创建时间 */
    @Schema(description = "订单创建时间")
    private String tradeCreateTime;

    /** 订单编号 */
    @Schema(description = "订单编号")
    private String tradeCode;

    @Schema(description = "分账状态")
    private LakalaLedgerStatus lakalaLedgerStatus;

    /**
     * 分账失败原因
     */
    private String lakalaLedgerFailReason;

    /** 订单类型（普通订单 积分订单） */
    @Schema(description = "订单类型")
    private String orderType;

    /** 订单类型 */
    @Schema(description = "订单类型", contentSchema = com.wanmi.sbc.account.bean.enums.TradeType.class)
    private String tradeType;

    /** 商品集合 */
    @Schema(description = "商品集合")
    private List<SettlementDetailGoodsViewVO> goodsViewList;

    /** 支付方式 */
    @Schema(description = "支付方式")
    private String payWayValue;

    /** 商家结算-供应商退单改价差额 */
    @Schema(description = "商家结算-供应商退单改价差额")
    private BigDecimal providerReturnSpecialPrice;

    /** 运费 */
    @Schema(description = "运费")
    private String deliveryPrice;

    /** 退货金额 */
    @Schema(description = "退货金额")
    private String returnPrice;

    /** 退单改价差额 */
    @Schema(description = "退单改价差额")
    private BigDecimal returnSpecialPrice;

    /** 店铺应收金额 */
    @Schema(description = "店铺应收金额")
    private BigDecimal storePrice;

    @Schema(description = "供货应收总额")
    private BigDecimal providerDeservePrice;

    /** 第三方供应商运费 */
    @Schema(description = "第三方供应商运费")
    private BigDecimal thirdPlatFormFreight = BigDecimal.ZERO.setScale(2);

    @Schema(description = "退单运费")
    private BigDecimal fee;

    @Schema(description = "分销员")
    private String distributorName;

    @Schema(description = "拉卡拉费率")
    private String lakalaRate;

    @Schema(description = "拉卡拉手续费")
    private String lakalaHandlingFee;

    @Schema(description = "分账状态")
    private String lakalaLedgerStatusStr;

    @Schema(description = "定金预售定金订单ID")
    private String bookingTid;

    /**
     * 运费优惠金额
     */
    @Schema(description = "运费优惠金额")
    private String freightCouponPrice;

    /**
     * 礼品卡抵扣金额
     */
    @Schema(description = "礼品卡抵扣金额")
    private String giftCardPrice;

    /**
     * 组装前台展示的实体
     *
     * @param settlementDetails
     * @return
     */
    public static List<LakalaSettlementDetailViewVO> renderSettlementDetailForView(
            List<LakalaSettlementDetailVO> settlementDetails, boolean isExcel) {
        if (settlementDetails == null || settlementDetails.isEmpty()) {
            return new ArrayList<>();
        }
        AtomicInteger count = new AtomicInteger();
        return settlementDetails.stream()
                .map(
                        detail -> {
                            LakalaSettlementDetailViewVO view = new LakalaSettlementDetailViewVO();

                            BigDecimal freightCouponPrice = detail.getSettleTrade().getFreightCouponPrice();
                            BigDecimal giftCardPrice = detail.getSettleTrade().getGiftCardPrice();

                            view.setId(detail.getId());
                            // 序号
                            view.setIndex((long) count.incrementAndGet());
                            // 下单时间
                            view.setTradeCreateTime(
                                    DateUtil.format(
                                            detail.getSettleTrade().getTradeCreateTime(),
                                            DateUtil.FMT_TIME_1));
                            // 订单编号
                            view.setTradeCode(detail.getSettleTrade().getTradeCode());
                            // 分账状态
                            view.setLakalaLedgerStatus(detail.getLakalaLedgerStatus());
                            switch (detail.getLakalaLedgerStatus()) {
                                case PROCESSING:
                                    view.setLakalaLedgerStatusStr("分账中");
                                    break;
                                case SUCCESS:
                                    view.setLakalaLedgerStatusStr("已分账");
                                    break;
                                case FAIL:
                                case INSUFFICIENT_AMOUNT:
                                    view.setLakalaLedgerStatusStr("分账失败");
                                    break;
                                case OFFLINE:
                                    view.setLakalaLedgerStatusStr("已线下分账");
                                    break;
                                default:
                                    view.setLakalaLedgerStatusStr("");
                                    break;
                            }
                            // 订单类型
                            view.setTradeType(
                                    detail.getSettleTrade().getTradeType() == TradeType.NORMAL_TRADE
                                            ? "普通"
                                            : "促销");
                            view.setOrderType(
                                    detail.getSettleTrade().getOrderType() == OrderType.POINTS_ORDER
                                            ? "积分订单"
                                            : "普通订单");
                            // 运费
                            BigDecimal deliveryPrice = detail.getSettleTrade().getDeliveryPrice();
                            view.setDeliveryPrice(
                                    deliveryPrice != null ? deliveryPrice.toString() : "-");

                            // 运费优惠金额
                            view.setFreightCouponPrice(freightCouponPrice != null ? freightCouponPrice.toString() : "-");

                            //礼品卡抵扣金额
                            view.setGiftCardPrice(giftCardPrice != null ? giftCardPrice.toString() : "-");
                            view.setGoodsViewList(
                                    detail.getSettleGoodList().stream()
                                            .map(
                                                    goods -> {
                                                        SettlementDetailGoodsViewVO goodsView =
                                                                new SettlementDetailGoodsViewVO();
                                                        // 商品名称
                                                        goodsView.setGoodsName(
                                                                goods.getGoodsName());
                                                        if (goods.getSkuNo() != null
                                                                && !"".equals(goods.getSkuNo())) {
                                                            // 商品编码
                                                            goodsView.setSkuNo(goods.getSkuNo());
                                                            if (isExcel) {
                                                                goodsView.setGoodsName(
                                                                        goods.getSkuNo()
                                                                                .concat(" ")
                                                                                .concat(
                                                                                        goodsView
                                                                                                .getGoodsName()));
                                                            }
                                                        }
                                                        if (goods.getSpecDetails() != null
                                                                && !""
                                                                        .equals(
                                                                                goods
                                                                                        .getSpecDetails())) {
                                                            // 商品规格
                                                            goodsView.setSpecDetails(
                                                                    goods.getSpecDetails());
                                                            if (isExcel) {
                                                                goodsView.setGoodsName(
                                                                        goodsView
                                                                                .getGoodsName()
                                                                                .concat(" ")
                                                                                .concat(
                                                                                        goods
                                                                                                .getSpecDetails()));
                                                            }
                                                        }
                                                        // 商品单价
                                                        goodsView.setGoodsPrice(
                                                                goods.getGoodsPrice());
                                                        // 商品类目
                                                        goodsView.setCateName(goods.getCateName());
                                                        if (Objects.nonNull(goods.getCateRate())) {
                                                            // 类目扣率
                                                            goodsView.setCateRate(
                                                                    goods.getCateRate()
                                                                            .toString()
                                                                            .concat("%"));
                                                            // 因为解析的时候是先算订单所得佣金，再算退单所还佣金，floor保留2位，保持统一
                                                            // 订单对应平台佣金
                                                            goodsView.setPlatformPrice(
                                                                    goods.getSplitPayPrice() != null
                                                                            ? // 现在按照实付金额算，不需要乘以数量
                                                                            goods.getSplitPayPrice()
                                                                                    .multiply(
                                                                                            goods
                                                                                                    .getCateRate())
                                                                                    .divide(
                                                                                            new BigDecimal(
                                                                                                    100))
                                                                                    .setScale(
                                                                                            2,
                                                                                            RoundingMode
                                                                                                    .FLOOR)
                                                                            : BigDecimal.ZERO);
                                                        } else {
                                                            goodsView.setCateRate(
                                                                    BigDecimal.ZERO
                                                                            .toString()
                                                                            .concat("%"));
                                                        }

                                                        // 商品数量
                                                        goodsView.setNum(goods.getNum());

                                                        // 计算营销和特价各个优惠价
                                                        if (detail.getSettleTrade().getOrderType()
                                                                != OrderType.POINTS_ORDER) {
                                                            setSpecialOrMarketingPrice(
                                                                    goodsView, goods);
                                                        }

                                                        // 商品实付
                                                        goodsView.setSplitPayPrice(
                                                                goods.getSplitPayPrice() != null
                                                                        ? goods.getSplitPayPrice()
                                                                        : BigDecimal.ZERO);

                                                        // 积分抵扣金额
                                                        goodsView.setPointPrice(
                                                                goods.getPointPrice());
                                                        // 积分数量
                                                        goodsView.setPoints(
                                                                Objects.isNull(goods.getPoints())
                                                                        ? 0L
                                                                        : goods.getPoints());
                                                        // 分销佣金
                                                        goodsView.setCommission(
                                                                goods.getCommission());
                                                        // 分销员
                                                        goodsView.setDistributorName(
                                                                goods.getDistributorName());
                                                        if (null != goods.getSupplyPrice()) {
                                                            // 供货金额
                                                            goodsView.setProviderPrice(
                                                                    goods.getSupplyPrice()
                                                                            .multiply(
                                                                                    new BigDecimal(
                                                                                            goods
                                                                                                    .getNum())));
                                                            goodsView.setSupplyPrice(goods.getSupplyPrice());
                                                        }
                                                        goodsView.setProviderName(
                                                                goods.getProviderName());
                                                        goodsView.setProviderPrice(
                                                                goods.getProviderPrice());
                                                        view.setDistributorName(
                                                                Objects.nonNull(
                                                                                goods
                                                                                        .getDistributorName())
                                                                        ? goods.getDistributorName()
                                                                        : null);
                                                        goodsView.setGiftCardPrice(goods.getGiftCardPrice());
                                                        return goodsView;
                                                    })
                                            .collect(Collectors.toList()));

                            // 店铺应收金额
                            BigDecimal storePrice = detail.getSettleTrade().getStorePrice();
                            view.setStorePrice(
                                    Objects.nonNull(storePrice) ? storePrice : BigDecimal.ZERO);

                            // 退单改差价金额
                            BigDecimal returnSpecialPrice =
                                    detail.getSettleTrade().getReturnSpecialPrice();
                            view.setReturnSpecialPrice(
                                    Objects.nonNull(returnSpecialPrice)
                                            ? returnSpecialPrice
                                            : BigDecimal.ZERO);

                            // 供应商退单改差价
                            BigDecimal providerReturnSpecialPrice =
                                    detail.getSettleTrade().getProviderReturnSpecialPrice();
                            view.setProviderReturnSpecialPrice(
                                    Objects.nonNull(providerReturnSpecialPrice)
                                            ? providerReturnSpecialPrice
                                            : BigDecimal.ZERO);

                            // 供货运费
                            BigDecimal thirdPlatFormFreight =
                                    detail.getSettleTrade().getThirdPlatFormFreight();
                            view.setThirdPlatFormFreight(thirdPlatFormFreight);

                            // 供货总额
                            BigDecimal providerPrice =
                                    view.getGoodsViewList().stream()
                                            .map(SettlementDetailGoodsViewVO::getProviderPrice)
                                            .filter(Objects::nonNull)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                            // 供货总额=供货金额+供货运费
                            if (thirdPlatFormFreight != null
                                    && thirdPlatFormFreight.compareTo(BigDecimal.ZERO) > 0) {
                                providerPrice = providerPrice.add(thirdPlatFormFreight);
                            }
                            view.setProviderDeservePrice(
                                    providerPrice.subtract(providerReturnSpecialPrice));
                            PayWay payWay = detail.getSettleTrade().getPayWay();
                            Long points = detail.getSettleTrade().getPoints();
                            StringBuilder sb = new StringBuilder();
                            BigDecimal splitPayPrice = BigDecimal.ZERO;

                            // 商品实付金额
                            if (CollectionUtils.isNotEmpty(view.getGoodsViewList())) {
                                splitPayPrice = view.getGoodsViewList().get(0).getSplitPayPrice();
                            }

                            if (Objects.nonNull(payWay)
                                    && splitPayPrice.compareTo(BigDecimal.ZERO) > 0) {
//                                if (Objects.nonNull(points)
//                                        && points > 0
//                                        && !payWay.equals(PayWay.POINT)) {
//                                    sb.append("积分+");
//                                }
                                sb.append(payWay.getDesc());
                            } else {
                                if (Objects.nonNull(points) && points > 0) {
                                    sb.append("积分");
                                } else {
                                    sb.append("-");
                                }
                            }
                            view.setPayWayValue(sb.toString());
                            view.setFee(detail.getFee());
                            view.setLakalaHandlingFee(detail.getLakalaHandlingFee());
                            view.setLakalaRate(detail.getLakalaRate());
                            view.setLakalaLedgerFailReason(detail.getLakalaLedgerFailReason());
                            return view;
                        })
                .collect(Collectors.toList());
    }

    /**
     * 按顺序执行，运算营销的优惠和特价 1。满减/满折 2。特价
     *
     * @param goodsView
     * @param settleGood
     */
    private static void setSpecialOrMarketingPrice(
            SettlementDetailGoodsViewVO goodsView, SettleGoodVO settleGood) {
        BigDecimal priceForSettle =
                settleGood.getGoodsPrice().multiply(new BigDecimal(settleGood.getNum()));
        // 按顺序获取优惠价格
        // 1.满折或者满减
        if (CollectionUtils.isNotEmpty(settleGood.getMarketingSettlements())) {
            for (SettleGoodVO.MarketingSettlement marketingSettlement :
                    settleGood.getMarketingSettlements()) {
                // 如果存在满减或者满折营销活动，计算满折/满减优惠价格
                if (marketingSettlement.getMarketingType() == SettleMarketingType.DISCOUNT) {
                    // 满折优惠
                    goodsView.setDiscountPrice(
                            priceForSettle
                                    .subtract(marketingSettlement.getSplitPrice())
                                    .toString());
                    priceForSettle = marketingSettlement.getSplitPrice();
                }

                if (marketingSettlement.getMarketingType() == SettleMarketingType.REDUCTION) {
                    // 满减优惠
                    goodsView.setReductionPrice(
                            priceForSettle
                                    .subtract(marketingSettlement.getSplitPrice())
                                    .toString());
                    priceForSettle = marketingSettlement.getSplitPrice();
                }
            }
        }
        // 2.优惠券
        if (CollectionUtils.isNotEmpty(settleGood.getCouponSettlements())) {
            // 先计算店铺券
            Optional<SettleGoodVO.CouponSettlement> couponSettle =
                    settleGood.getCouponSettlements().stream()
                            .filter(item -> item.getCouponType() == SettleCouponType.STORE_VOUCHERS)
                            .findFirst();
            if (couponSettle.isPresent()) {
                // 店铺优惠
                goodsView.setStoreCouponPrice(couponSettle.get().getReducePrice().toString());
                priceForSettle = couponSettle.get().getSplitPrice();
            }
            // 计算平台券
            couponSettle =
                    settleGood.getCouponSettlements().stream()
                            .filter(
                                    item ->
                                            item.getCouponType()
                                                    == SettleCouponType.GENERAL_VOUCHERS)
                            .findFirst();
            if (couponSettle.isPresent()) {
                // 平台优惠
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
        BigDecimal specialPrice =
                priceForSettle.subtract(
                        settleGood.getSplitPayPrice() != null
                                ? settleGood.getSplitPayPrice()
                                : BigDecimal.ZERO);
        switch (specialPrice.compareTo(BigDecimal.ZERO)) {
                // 大于0 -- 原价比特价高
            case 1:
                goodsView.setSpecialPrice("-".concat(specialPrice.toString()));
                break;
                // 等于0 -- 特价和原价相同
            case 0:
                goodsView.setSpecialPrice(specialPrice.toString());
                break;
                // 小于0 -- 原价比特价低
            case -1:
                goodsView.setSpecialPrice("+".concat(specialPrice.abs().toString()));
                break;
            default:
                goodsView.setSpecialPrice(specialPrice.toString());
                break;
        }
    }
}
