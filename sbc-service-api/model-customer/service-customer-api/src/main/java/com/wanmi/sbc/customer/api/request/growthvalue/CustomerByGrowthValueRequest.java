package com.wanmi.sbc.customer.api.request.growthvalue;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerByGrowthValueRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "会员成长值")
    @NotNull
    private Long growthValue;
}
