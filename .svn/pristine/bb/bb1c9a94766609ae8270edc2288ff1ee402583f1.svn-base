package com.wanmi.sbc.message.api.response.minimsgrecord;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据id查询任意是否推送response</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 推送状态 false 不推送 true 推送
     */
    @Schema(description = "推送状态 false 不推送 true 推送")
    private Boolean pullFlag;

    /**
     * 小程序模板ids
     */
    @Schema(description = "小程序模板ids")
    private List<String> templateIds;
}
