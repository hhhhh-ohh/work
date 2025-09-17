package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AccountType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengli
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeNameExistsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "员工名称")
    @NotNull
    private String employeeName;

    @Schema(description = "账号类型")
    @NotNull
    private AccountType accountType;
}
