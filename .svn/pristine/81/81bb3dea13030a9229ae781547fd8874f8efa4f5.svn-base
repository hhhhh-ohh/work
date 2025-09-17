package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class SwitchGetByIdRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "开关id")
    @NotNull
    private String id;
}
