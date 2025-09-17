package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AccountType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeBatchDeleteByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private List<String> employeeIds;

    /**
     * 账户类型
     */
    @Schema(description = "账户类型")
    private AccountType accountType;
}
