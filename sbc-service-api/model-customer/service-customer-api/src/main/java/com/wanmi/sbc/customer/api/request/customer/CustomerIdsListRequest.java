package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

@Schema
@Data
public class CustomerIdsListRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "会员ids")
    @NotNull
    private List<String> customerIds;
}
