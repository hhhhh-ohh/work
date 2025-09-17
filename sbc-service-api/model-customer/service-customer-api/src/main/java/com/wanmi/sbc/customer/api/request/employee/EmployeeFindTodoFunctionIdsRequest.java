package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

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
public class EmployeeFindTodoFunctionIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "员工信息")
    @NotNull
    private Operator operator;

    @Schema(description = "权限信息")
    private List<String> functionList;
}
