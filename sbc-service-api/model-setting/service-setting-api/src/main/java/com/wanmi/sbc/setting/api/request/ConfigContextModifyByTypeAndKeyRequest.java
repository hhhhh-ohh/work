package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigContextModifyByTypeAndKeyRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 配置键
     */
    @Schema(description = "配置键")
    @NotNull
    private ConfigKey configKey;

    /**
     * 类型
     */
    @Schema(description = "类型")
    @NotNull
    private ConfigType configType;

    /**
     * 配置内容
     */
    @Schema(description = "配置内容")
    @NotNull
    private String context;

    /**
     * 状态,0:未启用1:已启用
     */
    @Schema(description = "状态,0:未启用1:已启用")
    private Integer status;

}
