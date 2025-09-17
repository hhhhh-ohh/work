package com.wanmi.sbc.password;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @className UpdatePasswordRequest
 * @description TODO
 * @author 黄昭
 * @date 2021/9/29 15:40
 **/
@Data
@Schema(name = "UpdatePasswordRequest")
public class UpdatePasswordRequest extends BaseRequest {

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不可为空")
    private String phone;

    @Schema(description = "密码")
    @NotBlank(message = "密码不可为空")
    private String password;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不可为空")
    private String code;
}
