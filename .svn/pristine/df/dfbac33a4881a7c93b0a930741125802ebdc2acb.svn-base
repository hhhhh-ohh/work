package com.wanmi.sbc.account.api.request.finance.record;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.BaseRequest;
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
 * <p>对账单结构</p>
 * Created by daiyitian on 2018-10-26-下午4:02.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRecordAddRequest extends BaseRequest {

    private static final long serialVersionUID = -7472568313249418251L;
    @Schema(description = "对账单id")
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
     * 积分抵现金额
     */
    @Schema(description = "积分抵现金额")
    private BigDecimal pointsPrice;

    /**
     * 礼品卡抵扣金额
     */
    @Schema(description = "礼品卡抵扣金额")
    private BigDecimal giftCardPrice;

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
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTime;

    /**
     * 支付/退款时间
     */
    @Schema(description = "支付/退款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tradeTime;

    /**
     * 交易类型，0：收入 1：退款
     */
    @Schema(description = "交易类型", contentSchema = com.wanmi.sbc.account.bean.enums.AccountRecordType.class)
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

    @Schema(description = "礼品卡类型")
    private GiftCardType giftCardType;
}
