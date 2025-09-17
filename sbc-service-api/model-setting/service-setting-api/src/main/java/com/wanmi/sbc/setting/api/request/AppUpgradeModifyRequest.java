package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: chenli
 * @Date: Created In 上午10:06 2018/11/19
 * @Description: 检测升级更新配置 请求参数
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class AppUpgradeModifyRequest extends SettingBaseRequest{


    private static final long serialVersionUID = 1L;
    /**
     * APP强制更新开关 0 关，不强制更新 1 开，强制更新
     */
    @Schema(description = "APP强制更新开关-0 关，不强制更新 1 开，强制更新",contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    @NotNull
    private int forceUpdateFlag;

    /**
     * 最新版本号
     */
    @Schema(description = "最新版本号")
    @NotNull
    private String latestVersion;

    /**
     * Android下载地址
     */
    @Schema(description = "Android下载地址")
    @NotNull
    private String androidAddress;

    /**
     * App下载地址
     */
    @Schema(description = "App下载地址")
    @NotNull
    private String appAddress;

    /**
     * 更新描述
     */
    @Schema(description = "更新描述")
    @NotNull
    private String upgradeDesc;
}
