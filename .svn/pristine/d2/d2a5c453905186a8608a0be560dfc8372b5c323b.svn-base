package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.setting.bean.enums.ConfigType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class TradeConfigGetByTypeRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    @Schema(description = "系统配置KEY")
    @NotNull
    private ConfigType configType;
}
