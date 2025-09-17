package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributorLevelByCustomerIdRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 会员编号UUID
     */
    @NotNull
    private String customerId;
}