package com.wanmi.sbc.setting.api.request.statisticssetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @className QmStatisticsSettingRequest
 * @description
 * @author    张文昌
 * @date      2022/1/6 17:54
 */
@Schema
@Data
public class QmStatisticsSettingRequest extends BaseRequest {

    @Schema(description = "appkey")
    @NotBlank
    private String appKey;

    @Schema(description = "app_secret")
    @NotBlank
    private String appSecret;

    @Schema(description = "启用状态 0:未启用1:已启用")
    @NotNull
    private Integer status;

    /**
     * api地址
     */
    @Schema(description = "api地址")
    @NotBlank
    private String apiUrl;

}
