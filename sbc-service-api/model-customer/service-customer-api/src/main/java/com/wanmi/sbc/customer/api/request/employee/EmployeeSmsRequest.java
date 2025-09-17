package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSmsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 存入redis的验证码key
     */
    @Schema(description = "存入redis的验证码key")
    @NotBlank
    private String redisKey;

    /**
     * 要发送短信的手机号码
     */
    @Schema(description = "要发送短信的手机号码")
    @NotBlank
    private String mobile;

    /**
     * 短信内容模版
     */
    @Schema(description = "短信内容模版")
    @NotNull
    private SmsTemplate smsTemplate;

}
