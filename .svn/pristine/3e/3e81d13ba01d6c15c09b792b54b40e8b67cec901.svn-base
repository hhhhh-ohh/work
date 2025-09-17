package com.wanmi.sbc.customer.api.request.invoice;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员增票资质-根据用户ID查询Request
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @NotBlank
    private String customerId;

}
