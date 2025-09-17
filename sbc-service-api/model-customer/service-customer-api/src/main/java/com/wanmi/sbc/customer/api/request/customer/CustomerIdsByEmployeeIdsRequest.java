package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerIdsByEmployeeIdsRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "员工ID列表")
    @NotEmpty
    private List<String> employeeIds;

    @NotNull
    private DeleteFlag deleteFlag;
}
