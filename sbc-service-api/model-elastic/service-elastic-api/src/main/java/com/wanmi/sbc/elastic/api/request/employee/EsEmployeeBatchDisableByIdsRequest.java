package com.wanmi.sbc.elastic.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.customer.bean.enums.AccountState;

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
public class EsEmployeeBatchDisableByIdsRequest extends BaseRequest {


    /**
     * 员工Id
     */
    @Schema(description = "员工Id")
    private List<String> employeeIds;

    /**
     * 账号状态 0:启用 1:禁用
     */
    @Schema(description = "账号状态")
    private AccountState accountState;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    @CanEmpty
    private String accountDisableReason;
}
