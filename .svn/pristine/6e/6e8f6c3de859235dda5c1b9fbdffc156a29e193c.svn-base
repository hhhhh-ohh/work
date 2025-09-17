package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员登录注册-发送手机验证码Request
 */
@Schema
@Data
public class CustomerSendMobileCodeRequest extends CustomerBaseRequest implements Serializable {


    private static final long serialVersionUID = 1L;
    @Schema(description = "存入redis的验证码key")
    @NotBlank
    private String redisKey;

    @Schema(description = "要发送短信的手机号码")
    @NotBlank
    private String mobile;

    @Schema(description = "短信内容模版")
    @NotNull
    private SmsTemplate smsTemplate;
}
