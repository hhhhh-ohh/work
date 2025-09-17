package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.account.bean.enums.PayWay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema
public class ReconciliationVO extends BasicResponse {


    @Schema(description = "id")
    private String id;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long supplierId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayWay payWay;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal amount;

    /**
     * 积分
     */
    @Schema(description = "积分")
    private Long points;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private String customerId;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderCode;

    /**
     * 退单号
     */
    @Schema(description = "退单号")
    private String returnOrderCode;

    /**
     * 下单时间
     */
    @Schema(description = "下单时间")
    private LocalDateTime orderTime;

    /**
     * 支付/退款时间
     */
    @Schema(description = "支付/退款时间")
    private LocalDateTime tradeTime;

    /**
     * 交易类型，0：收入 1：退款
     */
    @Schema(description = "交易类型，0：收入 1：退款")
    private Byte type;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discounts;


    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;

    /**
     * 礼品卡抵扣金额
     */
    @Schema(description = "礼品卡抵扣金额")
    private BigDecimal giftCardPrice;
}
