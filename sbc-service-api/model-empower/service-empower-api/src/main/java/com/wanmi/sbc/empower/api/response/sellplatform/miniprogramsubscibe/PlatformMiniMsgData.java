package com.wanmi.sbc.empower.api.response.sellplatform.miniprogramsubscibe;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xufeng
 * @className PlatformMiniMsgData
 * @description TODO
 * @date 2022/8/10 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformMiniMsgData implements Serializable {

    private static final long serialVersionUID = 6832525865268955855L;

    /**
     * 添加至帐号下的模板 id，发送小程序订阅消息时所需
     */
    @Schema(description = "添加至帐号下的模板 id，发送小程序订阅消息时所需")
    private String priTmplId;

    /**
     * 模版标题
     */
    @Schema(description = "模版标题")
    private String title;

}