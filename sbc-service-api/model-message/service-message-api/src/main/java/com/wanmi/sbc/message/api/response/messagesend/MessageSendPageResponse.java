package com.wanmi.sbc.message.api.response.messagesend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.MessageSendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>站内信任务表分页结果</p>
 * @author xuyunpeng
 * @date 2020-01-06 11:12:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 站内信任务表分页结果
     */
    @Schema(description = "站内信任务表分页结果")
    private MicroServicePage<MessageSendVO> messageSendVOPage;

}
