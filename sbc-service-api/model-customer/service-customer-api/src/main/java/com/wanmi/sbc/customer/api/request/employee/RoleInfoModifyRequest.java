package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 会员角色-修改Request
 */
@Schema
@Data
public class RoleInfoModifyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    @NotNull
    private Long roleInfoId;
    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @NotBlank
    private String roleName;

    /**
     * 公司编号
     */
    @Schema(description = "公司编号")
    private Long companyInfoId;
}
