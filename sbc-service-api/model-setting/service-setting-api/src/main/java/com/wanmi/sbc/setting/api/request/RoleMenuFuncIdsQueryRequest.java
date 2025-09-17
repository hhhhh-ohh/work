package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuFuncIdsQueryRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 角色标识
     */
    @Schema(description = "角色标识")
    @NotNull
    private Long roleInfoId;
}
