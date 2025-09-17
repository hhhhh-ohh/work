package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员角色-修改Request
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfoDeleteRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    @NotNull
    private Long roleInfoId;

    /**
     * 公司编号
     */
    @Schema(description = "公司编号")
    private Long companyInfoId;
}
