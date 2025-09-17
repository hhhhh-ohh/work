package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员登录注册-验证手机号是否可发送验证码Request
 */
@Schema
@Data
public class CustomerValidateSendMobileCodeRequest extends CustomerBaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @NotBlank
    private String mobile;
}
