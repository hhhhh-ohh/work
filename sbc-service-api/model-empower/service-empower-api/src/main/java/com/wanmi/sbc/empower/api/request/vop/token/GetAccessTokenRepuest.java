package com.wanmi.sbc.empower.api.request.vop.token;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @className GetAccessTokenRepuest
 * @description 获取token请求参数
 * @author    张文昌
 * @date      2021/5/10 下午5:03
 */
@Schema
@Data
@Builder
public class GetAccessTokenRepuest {

    /**
     * 该值固定为access_token
     */
    @Schema(description = "该值固定为access_token")
    private String grant_type;

    /**
     * 对接账号
     */
    @Schema(description = "对接账号")
    private String client_id;

    /**
     * 当前时间，格式为“yyyy-MM-dd hh:mm:ss”
     */
    @Schema(description = "当前时间，格式为yyyy-MM-dd hh:mm:ss")
    private String timestamp;

    /**
     * 京东用户名
     */
    @Schema(description = "京东用户名")
    private String username;

    /**
     * 京东的密码，该字段需要把京东提供的密码进行32位md5加密，然后将结果转成小写进行传输。
     */
    @Schema(description = "京东的密码")
    private String password;

    /**
     * 签名
     */
    @Schema(description = "签名")
    private String sign;
}
