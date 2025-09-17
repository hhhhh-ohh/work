package com.wanmi.sbc.account.response;

import com.wanmi.sbc.common.base.BasicResponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单开票详情response
 * Created by CHENLI on 2017/5/8.
 */
@Schema
@Data
public class OrderInvoiceDetailResponse extends BasicResponse {

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

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
     * 付款状态0:已付款 1.未付款 2.待确认
     */
    @Schema(description = "付款状态", contentSchema = com.wanmi.sbc.order.bean.enums.PayState.class)
    private String payState;

    /**
     * 发票类型 0普通发票 1增值税专用发票
     */
    @Schema(description = "发票类型")
    private InvoiceType invoiceType;

    /**
     * 发票抬头
     */
    @Schema(description = "发票抬头")
    private String invoiceTitle;

    /**
     * 开票状态 0待开票 1 已开票
     */
    @Schema(description = "开票状态")
    private InvoiceState invoiceState;

    /**
     * 单位全称
     */
    @Schema(description = "单位全称")
    private String companyName;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    private String taxpayerNumber;

    /**
     * 单位电话
     */
    @Schema(description = "单位电话")
    private String companyPhone;

    /**
     * 单位地址
     */
    @Schema(description = "单位地址")
    private String companyAddress;

    /**
     * 银行基本户号
     */
    @Schema(description = "银行基本户号")
    private String bankNo;

    /**
     * 开户行
     */
    @Schema(description = "开户行")
    private String bankName;

    /**
     * 开票项目名称
     */
    @Schema(description = "开票项目名称")
    private String projectName;

    /**
     * 开票时间
     */
    @Schema(description = "开票时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 发票地址
     */
    @Schema(description = "发票地址")
    private String invoiceAddress;

    /**
     * 增专资质id
     */
    @Schema(description = "增专资质id")
    private Long customerInvoiceId;
}
