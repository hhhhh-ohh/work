package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class EmployeeLoginTimeModifyResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "总数")
    private int count;
}
