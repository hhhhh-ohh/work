package com.wanmi.sbc.third.login.response;

import com.wanmi.sbc.customer.response.LoginResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Schema
@Data
public class GetWeAppOpenIdResponse {

    @Schema(description = "openid")
    private String openid;

    /**
     * session_key,用来解密
     */
    @Schema(description = "sessionKey")
    private String sessionKey;


    @Schema(description = "unionId")
    private String unionId;

    /**
     * 小程序appId
     */
    @Schema(description = "appId")
    private String appId;

    /**
     * 小程序appSecret
     */
    @Schema(description = "appSecret")
    private String appSecret;


    /**
     * 登录还是注册，true为登录，false为注册
     */
    @Schema(description = "loginFlag")
    private Boolean loginFlag;

    @Schema(description = "微信返回结果")
    private WechatBaseInfoResponse info;

    @Schema(description = "登录返回结果")
    private LoginResponse login;
}
