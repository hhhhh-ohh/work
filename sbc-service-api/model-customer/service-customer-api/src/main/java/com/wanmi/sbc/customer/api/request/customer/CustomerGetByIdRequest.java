package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerGetByIdRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "客户ID")
    @NotNull
    private String customerId;
}
