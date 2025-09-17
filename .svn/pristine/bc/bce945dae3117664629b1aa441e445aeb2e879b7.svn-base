package com.wanmi.sbc.order.api.response.refund;

import com.wanmi.sbc.common.base.BasicResponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.enums.LogOutStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 退款单
 * Created by zhangjin on 2017/4/21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class RefundOrderResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(description = "退款单id")
    private String refundId;

    /**
     * 退单编号
     */
    @Schema(description = "退单编号")
    private String returnOrderCode;
    
    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;

    /**
     * 退款流水号
     */
    @Schema(description = "退款流水号")
    private String refundBillCode;

    /**
     * 退单下单时间
     */
    @Schema(description = "退单下单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    @Schema(description = "客户id")
    private String customerId;

    /**
     * 应退金额
     */
    @Schema(description = "应退金额")
    private BigDecimal returnPrice;

    /**
     * 实退金额
     */
    @Schema(description = "实退金额")
    private BigDecimal actualReturnPrice;

    /**
     * 应退积分
     */
    @Schema(description = "应退积分")
    private Long returnPoints;

    /**
     * 实退积分
     */
    @Schema(description = "实退积分")
    private Long actualReturnPoints;

    /**
     * 应退礼品卡金额
     */
    @Schema(description = "应退礼品卡金额")
    private BigDecimal giftCardPrice;

    /**
     * 退款账户
     */
    @Schema(description = "退款账户")
    private Long returnAccount;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String comment;

    /**
     * 退款状态
     */
    @Schema(description = "退款单状态")
    private RefundStatus refundStatus;

    /**
     * 退款方式
     */
    @Schema(description = "支付类型" )
    private PayType payType;

    /**
     * 退款账户
     */
    @Schema(description = "退款账户")
    private String returnAccountName;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    @SensitiveWordsField(signType = SignWordType.BANKNO)
    private String customerAccountName;

    /**
     * 线下平台账户
     */
    @Schema(description = "线下平台账户")
    private Long offlineAccountId;

    /**
     * 退款时间
     */
    @Schema(description = "退款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime refundBillTime;

    /**
     * 拒绝原因
     */
    @Schema(description = "拒绝原因")
    private String refuseReason;

    /**
     * 收款在线渠道
     */
    @Schema(description = "收款在线渠道")
    private String payChannel;

    @Schema(description = "收款在线渠道id")
    private Long payChannelId;

    /**
     * 收款在线渠道值
     */
    @Schema(description = "收款在线渠道")
    private String payChannelValue;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

}
