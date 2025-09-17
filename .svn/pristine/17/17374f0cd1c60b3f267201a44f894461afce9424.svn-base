package com.wanmi.sbc.customer.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by chenli on 2018/8/7.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginVerificationCodeRequest extends BaseRequest {

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank
    private String customerAccount;

    /**
     * 短信验证码
     */
    @Schema(description = "短信验证码")
    @NotBlank
    private String verificationCode;
}
