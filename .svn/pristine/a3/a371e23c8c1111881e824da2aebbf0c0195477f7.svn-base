package com.wanmi.sbc.customer.api.request.invoice;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 会员增票资质-根据纳税人识别号查询Request
 */
@Schema
@Data
public class CustomerInvoiceByTaxpayerNumberRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    @NotBlank
    private String taxpayerNumber;

}
