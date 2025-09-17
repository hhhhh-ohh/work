package com.wanmi.sbc.elastic.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EDZ
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsEmployeeModifyNameByIdRequest extends BaseRequest {

    /**
     * 员工Id
     */
    @Schema(description = "员工Id")
    private String employeeId;
    /**
     * 业务员名称
     */
    @Schema(description = "业务员名称")
    private String employeeName;
}
