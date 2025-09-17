package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListByHeirEmployeeIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "业务员ID列表")
    private List<String> employeeList;
}
