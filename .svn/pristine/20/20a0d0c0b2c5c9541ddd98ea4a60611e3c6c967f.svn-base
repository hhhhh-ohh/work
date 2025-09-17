package com.wanmi.sbc.customer.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

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
public class BalancePayPasswordSendCodeRequest extends BaseRequest {

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank
    private String customerAccount;

    /**
     * 是否是忘记密码 true：忘记密码 | false：
     */
    @Schema(description = "是否是忘记密码")
    private Boolean isForgetPassword;
}
