package com.wanmi.sbc.elastic.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.EmployeeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class EsEmployeeImportRequest extends BaseRequest {

    /**
     * 员工列表
     */
    @Schema(description = "员工列表")
    private List<EsEmployeeSaveRequest> employeeList;
}
