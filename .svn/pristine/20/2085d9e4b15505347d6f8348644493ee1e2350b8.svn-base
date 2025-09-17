package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class SwitchModifyRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 开关id
     */
    @Schema(description = "开关id")
    private String id;

    /**
     * 开关状态 0：关闭 1：开启
     */
    @Schema(description = "开关状态", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer status;
}
