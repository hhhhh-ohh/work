package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.EmployeePageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class EmployeePageResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "业务员列表")
    private MicroServicePage<EmployeePageVO> employeePageVOPage;
}
