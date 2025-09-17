package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleIdByEmployeeIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 员工Id
     */
    @Schema(description = "员工Id")
    @NotBlank
    private String employeeId;

}
