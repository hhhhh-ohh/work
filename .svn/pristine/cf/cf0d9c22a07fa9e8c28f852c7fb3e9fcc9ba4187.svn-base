package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 会员登录注册-登录Request
 */
@Schema
@Data
public class CustomerUpdateLoginTimeRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 账户Id
     */
    @Schema(description = "账户Id")
    @NotBlank
    private String customerId;

}
