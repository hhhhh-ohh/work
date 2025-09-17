package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.wanmi.sbc.customer.api.request.employee.EmployeeNameModifyRequest
 *
 * @author lipeng
 * @dateTime 2018/9/11 上午9:43
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeNameModifyByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    @NotNull
    protected String employeeId;

    /**
     * 员工姓名
     */
    @Schema(description = "员工姓名")
    private String employeeName;

}
