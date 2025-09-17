package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AccountType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeNumRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 公司id
     */
    @Schema(description = "公司id")
    private Long companyInfoId;

    /**
     * 账户类型
     */
    @Schema(description = "账户类型")
    private AccountType accountType;
}
