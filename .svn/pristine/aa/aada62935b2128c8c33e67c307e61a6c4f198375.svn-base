package com.wanmi.sbc.account.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>对账明细返回数据结构</p>
 * Created by of628-wenzhi on 2017-12-08-下午5:06.
 */
@Schema
@Data
public class AccountDetailsVO extends BasicResponse {

    private static final long serialVersionUID = -5172443033033451959L;
    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayWay payWay;

    /**
     * 金额,单位元，格式："￥#,###.00"
     */
    @Schema(description = "金额")
    private String amount;

    /**
     * 积分数量
     */
    @Schema(description = "积分数量")
    private Long points;

    /**
     * 积分抵现金额
     */
    @Schema(description = "积分抵现金额")
    private String pointsPrice;

    /**
     * 礼品卡抵扣金额
     */
    @Schema(description = "礼品卡抵扣金额")
    private String giftCardPrice;

    @Schema(description = "礼品卡类型")
    private GiftCardType giftCardType;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private String customerId;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderCode;

    /**
     * 下单/退单时间
     */
    @Schema(description = "下单/退单时间")
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
     * 优惠金额，单位元，格式："￥#,###.00"
     */
    @Schema(description = "优惠金额")
    private BigDecimal discounts;

    /**
     * 退单号
     */
    @Schema(description = "退单号")
    private String returnOrderCode;

    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;
}
