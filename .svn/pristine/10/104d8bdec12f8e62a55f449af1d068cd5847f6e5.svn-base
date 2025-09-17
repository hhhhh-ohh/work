package com.wanmi.sbc.third.login.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Data;

/**
 * 关联账号状态（目前只有微信，后期可以扩展qq、微博）
 * Created by gaomuwei on 2018/8/15.
 */
@Schema
@Data
@Builder
public class LinkedAccountFlagsQueryResponse {

    /**
     * 微信绑定状态
     */
    @Schema(description = "微信绑定状态")
    private Boolean wxFlag;

    @Schema(description = "头像路径")
    private String headimgurl;

    @Schema(description = "昵称")
    private String nickname;

}


