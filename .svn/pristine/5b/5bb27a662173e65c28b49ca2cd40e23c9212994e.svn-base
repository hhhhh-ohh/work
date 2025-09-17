package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AccountType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeByAccountNameRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "账户名称")
    @NotNull
    private String accountName;

    @Schema(description = "账号类型")
    @NonNull
    private AccountType accountType;

    @Schema(description = "账号类型集合")
    private List<AccountType> accountTypes;
}
