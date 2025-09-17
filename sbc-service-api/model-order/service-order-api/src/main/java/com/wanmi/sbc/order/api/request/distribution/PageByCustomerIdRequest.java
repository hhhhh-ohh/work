package com.wanmi.sbc.order.api.request.distribution;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Schema
@Data
public class PageByCustomerIdRequest extends BaseQueryRequest {

    @Schema(description = "分销员的customerId")
    @NotBlank
    private String customerId;

}
