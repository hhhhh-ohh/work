package com.wanmi.sbc.customer.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dyt on 2017/7/11.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest extends BaseRequest {

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank
    private String customerAccount;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotEmpty
    private char[] customerPassword;
}
