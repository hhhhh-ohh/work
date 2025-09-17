package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class EmployeeMobileSmsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    //true:可以发送，false:
    @Schema(description = "是否可以发送验证码")
    protected boolean isSendSms;
}
