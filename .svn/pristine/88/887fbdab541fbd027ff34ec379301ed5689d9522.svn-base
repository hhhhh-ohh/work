package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.customer.api.request.employee.EmployeeBatchEnableReqeust
 *
 * @author lipeng
 * @dateTime 2018/9/11 上午10:05
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeBatchEnableByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "员工编号")
    List<String> employeeIds;
}
