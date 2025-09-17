package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created by feitingting on 2019/1/4.
 */
@Schema
@Data
public class MiniProgramSetRequest extends BaseRequest {
    /**
     * APPID
     */
    @Schema(description = "APPID")
    private String appId;
    /**
     * APPSecret
     */
    @Schema(description = "APPSecret")
    private String appSecret;
    /**
     * 小程序配置的启用状态
     */
    @Schema(description = "小程序配置的启用状态", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private  Integer status;
}
