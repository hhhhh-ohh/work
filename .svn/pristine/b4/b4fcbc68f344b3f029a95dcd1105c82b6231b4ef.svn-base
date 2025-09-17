package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: chenli
 * @Date: Created In 上午10:06 2018/11/19
 * @Description: 检测升级更新配置 查询返回对象
 */
@Schema
@Data
public class AppUpgradeGetResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * APP强制更新开关 0 关，不强制更新 1 开，强制更新
     */
    @Schema(description = "APP强制更新开关-0 关，不强制更新 1 开，强制更新",contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private int forceUpdateFlag;

    /**
     * 最新版本号
     */
    @Schema(description = "最新版本号")
    private String latestVersion;

    /**
     * Android下载地址
     */
    @Schema(description = "Android下载地址")
    private String androidAddress;

    /**
     * App下载地址
     */
    @Schema(description = "App下载地址")
    private String appAddress;

    /**
     * 更新描述
     */
    @Schema(description = "更新描述")
    private String upgradeDesc;

}
