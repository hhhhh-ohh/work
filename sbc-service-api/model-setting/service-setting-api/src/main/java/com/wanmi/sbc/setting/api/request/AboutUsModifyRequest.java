package com.wanmi.sbc.setting.api.request;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author: songhanlin
 * @Date: Created In 09:58 2018/11/22
 * @Description: 关于我们修改请求Request
 */
@Schema
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AboutUsModifyRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 关于我们内容
     */
    @Schema(description = "关于我们内容")
    @NotNull
    private String context;
}
