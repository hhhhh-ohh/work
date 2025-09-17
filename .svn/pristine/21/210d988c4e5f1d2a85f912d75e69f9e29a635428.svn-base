package com.wanmi.sbc.empower.api.request.vop.token;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: xufan
 * @Date: 2020/2/25 18:03
 * @Description: 获取token请求参数
 *
 */
@Schema
@Data
@Builder
public class RefreshTokenRequest {

    /**
     * 授权时获取的refresh_token
     */
    @Schema(description = "授权时获取的refresh_token")
    private String refresh_token;

    /**
     * 对接账号
     */
    @Schema(description = "对接账号")
    private String client_id;

    /**
     * 对接密码
     */
    @Schema(description = "对接密码")
    private String client_secret;
}
