package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisableCustomerDetailGetByAccountRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "账户")
    @NotNull
    private String customerAccount;
}
