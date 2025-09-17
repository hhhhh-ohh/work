package com.wanmi.sbc.third.login.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.third.login.TerminalStringType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class WechatBindRequest extends BaseRequest {

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    String code;

    /**
     *  终端类型
     */
    @Schema(description = "终端类型")
    @NotNull
    TerminalStringType type;

    /**
     * 微信登录appId
     */
    @Schema(description = "微信登录appId")
    private String appId;

    /**
     * 微信登录appSecret
     */
    @Schema(description = "微信登录appSecret")
    private String appSecret;
}
