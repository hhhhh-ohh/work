package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPointsAvailableByIdRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "客户ID")
    @NotBlank
    private String customerId;
}
