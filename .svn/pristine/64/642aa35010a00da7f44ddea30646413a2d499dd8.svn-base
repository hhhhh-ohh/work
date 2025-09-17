package com.wanmi.sbc.account.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.GatherType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.enums.TradeType;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hht on 2017/12/6.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettleTradeVO extends BasicResponse {

    private static final long serialVersionUID = 3539828649407945522L;
    /**
     * 订单支付时间 -- trade - tradeStatus - payTime
     */
    @Schema(description = "订单支付时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tradePayTime;

    /**
     * 订单创建时间 -- trade - tradeStatus - createTime
     */
    @Schema(description = "订单创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tradeCreateTime;

    /**
     * 订单完成时间 -- trade - tradeStatus - endTime
     */
    @Schema(description = "订单完成时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tradeEndTime;

    /**
     * 订单入账时间
     */
    @Schema(description = "订单入账时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime finalTime;

    /**
     * 订单编号 -- trade - id
     */
    @Schema(description = "订单编号")
    private String tradeCode;

    /**
     * 订单类型 -- 普通
     */
    @Schema(description = "订单类型")
    private TradeType tradeType;

    /**
     * 订单类型 0：普通订单；1：积分订单
     */
    @Schema(description = "订单类型")
    private OrderType orderType;

    /**
     * 收款方 -- 平台
     */
    @Schema(description = "收款方")
    private GatherType gatherType;

    /**
     * 运费 -- trade - tradePrice - deliveryPrice
     */
    @Schema(description = "运费")
    private BigDecimal deliveryPrice;

    /**
     * 实际退款金额
     */
    @Schema(description = "实际退款金额")
    private BigDecimal returnPrice;

    /**
     * 订单交易总额
     */
    @Schema(description = "订单交易总额")
    private BigDecimal salePrice;

    /**
     * 退单改价差额
     */
    @Schema(description = "退单改价差额")
    private BigDecimal returnSpecialPrice;

    /**
     * 店铺应收金额
     */
    @Schema(description = "订单交易总额")
    private BigDecimal storePrice;

    /**
     * 商品供货总额
     * 商品供货总额
     */
    @Schema(description = "商品供货总额")
    private BigDecimal providerPrice;

    /**
     * 第三方供应商运费
     */
    @Schema(description = "第三方供应商运费")
    private BigDecimal thirdPlatFormFreight = BigDecimal.ZERO.setScale(2);

    /**
     * 订单积分
     */
    @Schema(description = "订单积分")
    private Long points;

    /**
     * 订单支付方式
     */
    @Schema(description = "订单支付方式")
    private PayWay payWay;

    /**
     * 商家结算-供应商退单改价差额
     */
    @Schema(description = "商家结算-供应商退单改价差额")
    private BigDecimal providerReturnSpecialPrice;

    /**
     * 运费优惠金额
     */
    @Schema(description = "运费优惠金额")
    private BigDecimal freightCouponPrice;

    /**
     * 礼品卡抵扣金额
     */
    @Schema(description = "礼品卡抵扣金额")
    private BigDecimal giftCardPrice = BigDecimal.ZERO;

    /**
     * 提货卡类型
     */
    private GiftCardType giftCardType;
}
