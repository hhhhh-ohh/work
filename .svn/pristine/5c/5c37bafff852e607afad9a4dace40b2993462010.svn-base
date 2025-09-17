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
public class BalancePayPasswordRequest extends BaseRequest {

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    @NotBlank
    private String customerId;

    /**
     * 支付密码
     */
    @Schema(description = "支付密码")
    @NotBlank
    private String customerPayPassword;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank
    private String verifyCode;


    /**
     * 是否是忘记密码 true：忘记密码 | false：
     */
    @Schema(description = "是否是忘记密码")
    private Boolean isForgetPassword;
}
