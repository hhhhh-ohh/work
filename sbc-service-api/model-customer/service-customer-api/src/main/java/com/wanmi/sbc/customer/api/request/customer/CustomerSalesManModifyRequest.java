package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class CustomerSalesManModifyRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "业务员id")
    private String employeeId;

    @Schema(description = "账号类型")
    private AccountType accountType;
}
