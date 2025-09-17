package com.wanmi.sbc.setting.bean.dto;

import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class PointsBasicRuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "系统配置Type")
    @NotNull
    private ConfigType configType;

    @Schema(description = "系统配置KEY")
    @NotNull
    private ConfigKey configKey;

    /**
     * 开关状态
     */
    @Schema(description = "开关状态", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    @NotNull
    private Integer status;

    /**
     * 规则说明
     */
    @Schema(description = "规则说明")
    private String context;
}
