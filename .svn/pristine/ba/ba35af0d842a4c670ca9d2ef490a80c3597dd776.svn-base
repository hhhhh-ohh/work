package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询用户是否包含此接口
 * Author: bail
 * Time: 2018/1/9.14:02
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorityRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 接口路径
     */
    @Schema(description = "接口路径")
    private String urlPath;

    /**
     * 请求类型
     */
    @Schema(description = "请求类型-GET,POST,PUT,DELETE")
    private String requestType;
}