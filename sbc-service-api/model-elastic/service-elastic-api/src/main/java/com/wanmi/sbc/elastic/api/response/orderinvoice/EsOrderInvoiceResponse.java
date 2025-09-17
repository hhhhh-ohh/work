package com.wanmi.sbc.elastic.api.response.orderinvoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单开票返回列表
 *
 * @author CHENLI
 * @date 2017/5/5
 */
@Schema
@Data
public class EsOrderInvoiceResponse extends BasicResponse {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String orderInvoiceId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
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
    private String orderNo;

    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal orderPrice;

    /**
     * 流程状态（订单状态）
     */
    @Schema(description = "流程状态（订单状态）")
    private String flowState;

    /**
     * 付款状态0:已收款 1.未收款 2.待确认
     */
    @Schema(description = "付款状态")
    private Integer payOrderStatus;

    /**
     * 发票类型 0普通发票 1增值税专用发票
     */
    @Schema(description = "发票类型")
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    @Schema(description = "发票抬头")
    private String invoiceTitle;

    /**
     * 开票状态 0待开票 1 已开票
     */
    @Schema(description = "开票状态")
    private Integer invoiceState;

    /**
     * 开票时间
     */
    @Schema(description = "开票时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime invoiceTime;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;


    /**
     * 门店/店铺名称
     */
    @Schema(description = "门店/店铺名称")
    private String storeName;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;
}