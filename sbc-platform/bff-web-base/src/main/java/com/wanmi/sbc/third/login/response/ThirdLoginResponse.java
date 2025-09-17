package com.wanmi.sbc.third.login.response;

import com.wanmi.sbc.customer.response.LoginResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Schema
@Data
public class ThirdLoginResponse {

    @Schema(description = "是否登录")
    private Boolean loginFlag;

    @Schema(description = "微信返回结果")
    private WechatBaseInfoResponse info;

    @Schema(description = "登录返回结果")
    private LoginResponse login;

    @Schema(description = "登录凭证")
    private String token;

    @Schema(description = "unionId")
    private String unionId;

    @Schema(description = "手机号")
    private String phoneNum;
}
