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
public class ConfigStatusModifyByTypeAndKeyRequest extends SettingBaseRequest {
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
     * 状态
     */
    @Schema(description = "状态", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    @NotNull
    private Integer status;

    /**
     * 是否展示
     */
    @Schema(description = "是否展示")
    private Integer isShow;
}
