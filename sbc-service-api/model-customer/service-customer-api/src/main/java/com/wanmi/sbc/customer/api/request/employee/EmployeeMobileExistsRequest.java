package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AccountType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeMobileExistsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "员工手机号")
    @NotNull
    private String mobile;

    @Schema(description = "账号类型")
    @NotNull
    private AccountType accountType;

    @Schema(description = "公司id")
    private Long companyInfoId;
}
