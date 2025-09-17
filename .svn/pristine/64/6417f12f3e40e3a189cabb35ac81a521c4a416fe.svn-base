package com.wanmi.sbc.message.api.response.messagesend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.MessageSendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>站内信任务表列表结果</p>
 * @author xuyunpeng
 * @date 2020-01-06 11:12:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 站内信任务表列表结果
     */
    @Schema(description = "站内信任务表列表结果")
    private List<MessageSendVO> messageSendVOList;
}
