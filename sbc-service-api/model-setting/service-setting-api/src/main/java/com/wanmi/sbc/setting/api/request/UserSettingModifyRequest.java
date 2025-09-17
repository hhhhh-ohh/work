package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改用户设置
 */
@Schema
@Data
public class UserSettingModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 需要登录访问商城开关设置
     */
    @Schema(description = "需要登录访问商城开关设置")
    @NotNull
    private BoolFlag visitWithLoginStatus;
}
