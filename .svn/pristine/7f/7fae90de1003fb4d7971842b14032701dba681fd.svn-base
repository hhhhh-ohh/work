package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 修改用户设置
 */
@Schema
@Data
public class MiniSettingModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 小程序分享内容设置
     */
    @Schema(description = "小程序分享内容设置")
    @NotBlank
    private String appletShareSettingContext;
}
