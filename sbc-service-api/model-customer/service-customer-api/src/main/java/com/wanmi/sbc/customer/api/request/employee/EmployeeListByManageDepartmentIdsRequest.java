package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListByManageDepartmentIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 管理部门
     */
    @Schema(description = "管理部门")
    @NotBlank
    private String manageDepartmentIds;
}
