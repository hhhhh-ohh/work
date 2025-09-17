package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

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
public class EmployeeListByHeirEmployeeIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "交接人ID")
    private List<String> heirEmployeeId;

}
