package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleInfoAndMenuInfoListRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 角色标识
     */
    @Schema(description = "角色标识")
    @NotNull
    private List<Long> roleInfoIdList;
}
