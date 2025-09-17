package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class ConfigUpdateRequest extends BaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 键
     */
    @Schema(description = "键")
    private String configKey;


    /**
     * 状态
     */
    @Schema(description = "状态", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer status;


}
