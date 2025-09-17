package com.wanmi.sbc.third.login.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Data;

@Schema
@Data
@Builder
public class WechatSetDetailResponse {

    /**
     * 微信appId
     */
    @Schema(description = "微信appId")
    private String appId;
}
