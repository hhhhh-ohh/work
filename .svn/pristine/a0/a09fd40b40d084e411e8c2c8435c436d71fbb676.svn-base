package com.wanmi.sbc.customer.api.request.invoice;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 会员增票资质-根据用户ID查询Request
 */
@Schema
@Data
public class CustomerInvoiceByCustomerIdAndDelFlagRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @NotBlank
    private String customerId;

    private InvoiceStyle invoiceStyle;

}
