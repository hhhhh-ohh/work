package com.wanmi.sbc.third.login.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.io.Serializable;

/**
 * 小程序授权
 * @version 0.1.0
 */
@Schema
@Data
public class LittleProgramAuthResponse implements Serializable {

    @Schema(description = "用户唯一标识")
    private String openid;

    @Schema(description = "会话密钥")
    private String session_key;

    @Schema(description = "用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回")
    private String unionid;

    @Schema(description = "错误码")
    private Long   errcode;

    @Schema(description = "错误信息")
    private String errMsg;
}
