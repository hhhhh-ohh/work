package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.EmployeeDisableOrEnableByCompanyIdVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
public class EmployeeDisableOrEnableByCompanyIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "业务员列表")
    private List<EmployeeDisableOrEnableByCompanyIdVO> employeeList;
}
