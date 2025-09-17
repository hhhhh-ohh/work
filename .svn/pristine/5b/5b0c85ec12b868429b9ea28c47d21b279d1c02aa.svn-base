package com.wanmi.sbc.account.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.FlowState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-17 10:43
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInvoiceViewResponse extends BasicResponse {

    /**
     * 用户姓名
     */
    @Schema(description = "用户姓名")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    private String customerId;

    /**
     * 开票时间
     */
    @Schema(description = "开票时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime invoiceTime;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderNo;

    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal orderPrice;

    /**
     * 付款状态
     */
    @Schema(description = "付款状态")
    private PayOrderStatus payOrderStatus;

    /**
     * 流程状态（订单状态）
     */
    @Schema(description = "流程状态（订单状态）")
    private FlowState flowState;

    /**
     * 发票类型
     */
    @Schema(description = "发票类型")
    private InvoiceType invoiceType;

    /**
     * 发票title
     */
    @Schema(description = "发票title")
    private String invoiceTitle;

    /**
     * 纳税识别号
     */
    @Schema(description = "纳税识别号")
    private String taxNo;

    /**
     * 注册地址
     */
    @Schema(description = "注册地址")
    private String registerAddress;

    /**
     * 注册电话
     */
    @Schema(description = "注册电话")
    private String registerPhone;

    /**
     * 银行账户号
     */
    @Schema(description = "银行账户号")
    private String bankNo;

    /**
     * 银行名称
     */
    @Schema(description = "银行名称")
    private String bankName;

    /**
     * 发票寄送地址
     */
    @Schema(description = "发票寄送地址")
    @SensitiveWordsField(signType = SignWordType.ADDRESS)
    private String invoiceAddress;

    /**
     * 开票项目
     */
    @Schema(description = "开票项目")
    private String projectName;

    /**
     * 订单开票状态
     */
    @Schema(description = "订单开票状态")
    private InvoiceState invoiceState;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 公司信息id
     */
    @Schema(description = "公司信息id")
    private Long companyInfoId;
}
