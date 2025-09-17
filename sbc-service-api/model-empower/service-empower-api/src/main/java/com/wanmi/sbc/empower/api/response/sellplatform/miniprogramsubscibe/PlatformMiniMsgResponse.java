package com.wanmi.sbc.empower.api.response.sellplatform.miniprogramsubscibe;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xufeng
 * @className PlatformMiniMsgResponse
 * @description TODO
 * @date 2022/8/10 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformMiniMsgResponse implements Serializable {

    private static final long serialVersionUID = 8720188898085434444L;

    /**
     * 错误码
     */
    @Schema(description = "错误码")
    private String errcode;

    /**
     * 错误信息
     */
    @Schema(description = "错误信息")
    private String errmsg;

    /**
     * 个人模板列表
     */
    @Schema(description = "个人模板列表")
    private List<PlatformMiniMsgData> data;
}