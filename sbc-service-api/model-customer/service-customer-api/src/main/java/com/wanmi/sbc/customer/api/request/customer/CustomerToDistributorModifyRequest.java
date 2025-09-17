package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Schema
@Data
@Builder
public class CustomerToDistributorModifyRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    @NotNull
    private String customerId;

    /**
     * 是否为分销员
     */
    @Schema(description = "是否为分销员")
    @NotNull
    private DefaultFlag isDistributor;
}
