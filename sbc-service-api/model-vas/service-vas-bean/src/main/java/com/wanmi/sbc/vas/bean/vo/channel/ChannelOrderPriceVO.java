package com.wanmi.sbc.vas.bean.vo.channel;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单金额归总
 * Created by jinwei on 19/03/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelOrderPriceVO extends BasicResponse {

    /**
     * 商品总金额
     */
    @Schema(description = "商品总金额")
    private BigDecimal goodsPrice;

    /**
     * 配送费用，可以从TradePriceInfo获取
     */
    @Schema(description = "配送费用")
    private BigDecimal deliveryPrice;

    /**
     * 特价金额，可以从TradePriceInfo获取
     */
    @Schema(description = "特价金额")
    private BigDecimal privilegePrice;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountsPrice;

    /**
     * 积分数量，可以从TradePriceInfo获取(此变量没用到)
     */
    @Schema(description = "积分数量")
    private Integer integral;

    /**
     * 积分兑换金额，可以从TradePriceInfo获取(此变量没用到)
     */
    @Schema(description = "积分兑换金额")
    private BigDecimal integralPrice;

    /**
     * 积分
     */
    @Schema(description = "积分")
    private Long points;

    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoints;

    /**
     * 积分兑换金额
     */
    @Schema(description = "积分兑换金额")
    private BigDecimal pointsPrice;

    /**
     * 积分价值
     */
    @Schema(description = "积分价值")
    private Long pointWorth;

    /**
     * 是否特价单
     */
    @Schema(description = "是否特价单",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private boolean special;

    /**
     * 是否开启运费
     */
    @Schema(description = "是否开启运费",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private boolean enableDeliveryPrice;

    /**
     * 原始金额, 不作为付费金额
     */
    @Schema(description = "原始金额, 不作为付费金额")
    private BigDecimal originPrice;

    /**
     * 订单应付金额
     */
    @Schema(description = "订单应付金额")
    private BigDecimal totalPrice;

    /**
     * 订单实际支付金额
     * 账务中心每次回调的支付金额之和：订单已支付金额
     * add wumeng
     */
    @Schema(description = "订单实际支付金额")
    private BigDecimal totalPayCash;

    /**
     * 支付手续费
     * 账务中心支付时银行收取的支付费率
     * add wumeng
     */
    @Schema(description = "支付手续费")
    private BigDecimal rate;

    /**
     * 平台佣金
     * add wumeng
     */
    @Schema(description = "平台佣金")
    private BigDecimal cmCommission;

    /**
     * 发票费用
     * added by shenchunnan
     */
    @Schema(description = "发票费用")
    private BigDecimal invoiceFee;

    /**
     * 优惠券优惠金额
     */
    @Schema(description = "优惠券优惠金额")
    private BigDecimal couponPrice = BigDecimal.ZERO;

    /**
     * 单个订单返利总金额
     */
    @Schema(description = "单个订单返利总金额")
    private BigDecimal orderDistributionCommission = BigDecimal.ZERO;

    /**
     * 订单供货价总额
     */
    @Schema(description = "订单供货价总额")
    private BigDecimal orderSupplyPrice;

    /**
     * 定金
     */
    @Schema(description = "定金")
    private BigDecimal earnestPrice;

    /**
     * 定金膨胀
     */
    @Schema(description = "定金膨胀")
    private BigDecimal swellPrice;

    /**
     * 尾款
     */
    @Schema(description = "尾款")
    private BigDecimal tailPrice;


    /**
     * 可退定金
     */
    @Schema(description = "可退定金")
    private BigDecimal canBackEarnestPrice;

    /**
     * 可退尾款
     */
    @Schema(description = "可退尾款")
    private BigDecimal canBackTailPrice;

    /**
     * 活动优惠总额
     */
    @Schema(description = "活动优惠总额")
    private BigDecimal marketingDiscountPrice;

    /**
     * 原配送费用，改价之后填充
     */
    @Schema(description = "原配送费用，改价之后填充")
    private BigDecimal oldDeliveryPrice;

    /**
     * 原订单应付金额，改价之后填充
     */
    @Schema(description = "原订单应付金额，改价之后填充")
    private BigDecimal oldTotalPrice;
}
