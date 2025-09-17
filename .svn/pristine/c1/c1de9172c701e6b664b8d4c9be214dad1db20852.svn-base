package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigModifyRequest extends BaseRequest {
    /**
     * 配置键ID
     */
    @Schema(description = "配置键ID")
    @NotNull(message = "配置键ID不可为空")
    private Long id;

    /**
     * 配置键
     */
    @Schema(description = "配置键")
    private ConfigKey configKey;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private ConfigType configType;

    /**
     * 状态
     */
    @Schema(description = "状态", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer status;

    @Schema(description = "内容")
    private String context;

}
