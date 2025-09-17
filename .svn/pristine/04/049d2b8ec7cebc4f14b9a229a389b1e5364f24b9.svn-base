package com.wanmi.sbc.customer.api.request.invoice;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 会员增票资质-根据增票资质ID查询Request
 */
@Schema
@Data
public class CustomerInvoiceByIdAndDelFlagRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 增票ID
     */
    @Schema(description = "增票ID")
    @NotNull
    private Long customerInvoiceId;

}
