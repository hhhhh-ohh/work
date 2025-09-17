package com.wanmi.sbc.third.login.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Schema
@Data
public class WechatBaseInfoResponse {

    @Schema(description = "微信unionid")
    private String id;

    @Schema(description = "微信昵称")
    private String name;

    @Schema(description = "微信头像路径")
    private String headImgUrl;
}
