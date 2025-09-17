package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeChangeDepartmentRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 会员id列表
     */
    @Schema(description = "会员id列表")
    @NotNull
    private List<String> employeeIds;

    /**
     * 部门id列表
     */
    @Schema(description = "部门id列表")
    @NotNull
    private List<String> departmentIds;
}
