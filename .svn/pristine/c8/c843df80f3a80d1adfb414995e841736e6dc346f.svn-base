package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class EmployeeMobileExistsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "是否存在")
    private boolean exists;

    @Schema(description = "若存在，这里区分存在于其他商家，还是存在于当前商家（用于前端提示）")
    private Boolean inOtherCompanyFlagIfPresent;
}
