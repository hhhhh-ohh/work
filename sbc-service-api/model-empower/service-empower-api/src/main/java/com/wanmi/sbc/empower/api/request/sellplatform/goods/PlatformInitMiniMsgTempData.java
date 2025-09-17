package com.wanmi.sbc.empower.api.request.sellplatform.goods;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  初始化小程序订阅消息模板
 * @author  xufeng
 * @date: 2022/8/10 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformInitMiniMsgTempData {

    private static final long serialVersionUID = -5822115089100969813L;

    /**
     * 模版名称
     */
    @Schema(description = "模版名称")
    private String templateName;

    @Schema(description = "模版标题ID")
    private String tid;

    @Schema(description = "开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如 [3,5,4] 或 [4,5,3]），最多支持5个，最少2个关键词组合")
    private String kidList;

}
