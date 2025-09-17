package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorityDeleteRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "主键")
    @NotNull
    private String authorityId;
}
