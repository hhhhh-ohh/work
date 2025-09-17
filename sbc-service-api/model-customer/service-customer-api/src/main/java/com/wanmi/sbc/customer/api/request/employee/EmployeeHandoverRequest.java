package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

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
public class EmployeeHandoverRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 原业务员集合
     */
    @Schema(description = "原业务员集合")
    private List<String> employeeIds;

    /**
     * 新业务原id
     */
    @Schema(description = "新业务原id")
    private String newEmployeeId;
}
