package com.wanmi.sbc.account.request;

import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.math.BigDecimal;

@Schema
@Data
public class OrderInvoiceSaveRequest extends BaseRequest {

    /**
     * 订单开票ID
     */
    @Schema(description = "订单开票ID")
    private String orderInvoiceId;

    /**
     * 发票类型
     */
    @Schema(description = "发票类型")
    @NotNull(message = "发票类型不可为空")
    private InvoiceType invoiceType;

    /**
     * 开票项id
     */
    @Schema(description = "开票项id")
    @NotNull(message = "开票项id不可为空")
    private String projectId;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String customerId;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @NotBlank(message = "订单编号不可为空")
    private String orderNo;

    /**
     * 发票title
     */
    @Schema(description = "发票title")
    private String invoiceTitle;

    /**
     * 发票地址
     */
    @Schema(description = "发票地址")
    @NotBlank(message = "发票地址不可为空")
    private String invoiceAddress;

    /**
     * 开票时间
     */
    @Schema(description = "开票时间")
    @NotBlank(message = "开票时间不可为空")
    private String invoiceTime;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    private String contacts;

    /**
     * 收货人联系号码
     */
    @Schema(description = "收货人联系号码")
    private String phone;

    /**
     * 收货地址
     */
    @Schema(description = "收货地址")
    private String address;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId = 0L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId = 0L;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    private String taxpayerNumber;

    /**
     * 订单开票收货地址
     */
    @Schema(description = "订单开票收货地址")
    private String addressInfoId;

    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal orderPrice;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    private String supplierName;

    @Schema(description = "支付状态")
    private Integer payOrderStatus;

}
