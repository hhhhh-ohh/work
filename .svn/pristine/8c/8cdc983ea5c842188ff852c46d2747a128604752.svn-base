package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 会员支付密码校验参数入参
 */
@Schema
@Data
public class CustomerCheckPayPasswordRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;


    /**
     * 客户ID
     */
    @Schema(description = "客户ID", hidden = true)
    private String customerId;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank
    private String payPassword;
}
