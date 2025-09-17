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
public class EsEmployeeBatchDimissionByIdsRequest extends BaseRequest {

    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private List<String> employeeIds;

    /**
     * 账户类型
     */
    @Schema(description = "账户类型")
    private AccountState accountState;

    /**
     * 离职原因
     */
    @Schema(description = "离职原因")
    @CanEmpty
    private String accountDimissionReason;
}
