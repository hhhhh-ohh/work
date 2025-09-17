package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeMobileSmsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 要发送短信的手机号码
     */
    @Schema(description = "要发送短信的手机号码")
    @NotBlank
    private String mobile;

}
