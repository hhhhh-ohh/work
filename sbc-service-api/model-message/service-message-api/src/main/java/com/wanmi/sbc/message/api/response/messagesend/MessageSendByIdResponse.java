package com.wanmi.sbc.message.api.response.messagesend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.MessageSendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）站内信任务表信息response</p>
 * @author xuyunpeng
 * @date 2020-01-06 11:12:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 站内信任务表信息
     */
    @Schema(description = "站内信任务表信息")
    private MessageSendVO messageSendVO;


}
