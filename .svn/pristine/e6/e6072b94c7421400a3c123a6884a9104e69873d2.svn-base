package com.wanmi.sbc.empower.api.response.vop.token;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xufan
 * @Date: 2020/2/25 18:40
 * @Description: 获取token响应参数
 *
 */
@Schema
@Data
public class RefreshTokenResponse implements Serializable {

    private static final long serialVersionUID = -8256967929682702954L;
    /**
     * 业务id
     */
    @Schema(description = "业务id")
    private String uid;

    /**
     * 访问令牌，用于业务接口调用
     */
    @Schema(description = "访问令牌，用于业务接口调用")
    private String access_token;

    /**
     * 当access_token过期时，用于刷新access_token
     */
    @Schema(description = "当access_token过期时，用于刷新access_token")
    private String refresh_token;

    /**
     * 当前时间，时间戳格式：1551663377887
     */
    @Schema(description = "当前时间，时间戳格式：1551663377887")
    private Long time;

    /**
     * access_token的有效期，单位：秒，有效期24小时
     */
    @Schema(description = "access_token的有效期，单位：秒，有效期24小时")
    private Integer expires_in;

    /**
     * refresh_token的过期时间，毫秒级别,时间戳
     */
    @Schema(description = "refresh_token的过期时间，毫秒级别,时间戳")
    private Long refresh_token_expires;
}
