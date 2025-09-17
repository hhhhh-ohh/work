package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.validGroups.NotCustomerAccount;
import com.wanmi.sbc.customer.validGroups.NotVerify;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
public class RegisterCheckRequest extends BaseRequest {

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank(groups = {NotCustomerAccount.class})
    private String customerAccount;


    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(groups = {NotVerify.class})
    private String verifyCode;

    /**
     * 注册来源,0:注册页面，1：注册弹窗
     */
    @Schema(description = "验证码")
    @NotNull
    private Integer fromPage;
}
