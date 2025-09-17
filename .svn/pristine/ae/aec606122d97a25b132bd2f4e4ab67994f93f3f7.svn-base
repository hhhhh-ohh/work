package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.enums.Platform;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuAndFunctionListRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 平台类型
     */
    @Schema(description = "平台类型")
    @NotNull
    private Platform systemTypeCd;
}
