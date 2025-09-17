package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class CustomerIdListRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "负责业务员")
    @NotNull
    private String employeeId;
}
