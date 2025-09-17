package com.wanmi.sbc.order.trade.model.entity.value;

import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单金额归总
 * Created by jinwei on 19/03/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradePrice implements Serializable {

    /**
     * 商品总金额
     */
    private BigDecimal goodsPrice;

    /**
     * 配送费用，
     */
    private BigDecimal deliveryPrice;

    /**
     * 特价金额，
     */
    private BigDecimal privilegePrice;

    /**
     * 优惠金额
     */
    private BigDecimal discountsPrice;

    /**
     * 积分数量
     */
    private Integer integral;

    /**
     * 积分兑换金额
     */
    private BigDecimal integralPrice;

    /**
     * 积分
     */
    private Long points;

    /**
     * 购买积分
     */
    private Long buyPoints;

    /**
     * 积分兑换金额
     */
    private BigDecimal pointsPrice;

    /**
     * 积分价值
     */
    private Long pointWorth;

    /**
     * 是否特价单
     */
    private boolean special;

    /**
     * 是否开启运费
     */
    private boolean enableDeliveryPrice;

    /**
     * 原始金额, 不作为付费金额
     */
    private BigDecimal originPrice;

    /**
     * 订单应付金额
     */
    private BigDecimal totalPrice;

    /**
     * 订单实际支付金额
     * 账务中心每次回调的支付金额之和：订单已支付金额
     * 定金预售订单：totalPayCash为尾款金额
     */
    private BigDecimal totalPayCash;

    /**
     * 支付手续费
     * 账务中心支付时银行收取的支付费率
     * add wumeng
     */
    private BigDecimal rate;

    /**
     * 平台佣金
     * add wumeng
     */
    private BigDecimal cmCommission;

    /**
     * 发票费用
     * added by shenchunnan
     */
    private BigDecimal invoiceFee;

    /**
     * 订单优惠金额明细
     */
    private List<DiscountsPriceDetail> discountsPriceDetails;

    /**
     * 优惠券优惠金额
     */
    private BigDecimal couponPrice = BigDecimal.ZERO;

    /**
     * 商家券优惠金额  =   商品券优惠金额（couponPrice）+运费券优惠金额（freightCouponPrice）
     */
    private BigDecimal storeCouponPrice = BigDecimal.ZERO;

    /**
     * 运费券优惠金额
     */
    private BigDecimal freightCouponPrice = BigDecimal.ZERO;

    /**
     * 订单供货价总额
     */
    private BigDecimal orderSupplyPrice;


    /**
     * 定金
     */
    private BigDecimal earnestPrice;

    /**
     * 定金膨胀
     */
    private BigDecimal swellPrice;

    /**
     * 尾款
     */
    private BigDecimal tailPrice;


    /**
     * 可退定金
     */
    private BigDecimal canBackEarnestPrice;

    /**
     * 可退尾款
     */
    private BigDecimal canBackTailPrice;

    /**
     * 活动优惠总额
     */
    private BigDecimal marketingDiscountPrice;

    /**
     * 原配送费用，改价之后填充
     */
    private BigDecimal oldDeliveryPrice;

    /**
     * 原订单应付金额，改价之后填充
     */
    private BigDecimal oldTotalPrice;
    /**
     * 订单综合税费
     */
    private BigDecimal totalTax;

    /**
     * 帮砍金额
     */
    private BigDecimal bargainPrice;

    /**
     * 礼品卡抵扣金额
     */
    private BigDecimal giftCardPrice;

    /**
     * 礼品卡类型
     */
    private GiftCardType giftCardType;
}
