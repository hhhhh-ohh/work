package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.dto.ThirdLoginRelationDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员登录注册-绑定第三方账号Request
 */
@Schema
@Data
public class CustomerLoginAndBindThirdAccountRequest extends CustomerBaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @NotBlank
    private String  phone;

    @Schema(description = "第三方登录-共用DTO")
    @NotNull
    private ThirdLoginRelationDTO thirdLoginRelationDTO;

    @Schema(description = "分享人用户id")
    private String shareUserId;
}
