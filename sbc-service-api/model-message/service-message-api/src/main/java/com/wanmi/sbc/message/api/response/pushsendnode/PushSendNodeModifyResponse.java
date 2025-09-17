package com.wanmi.sbc.message.api.response.pushsendnode;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.PushSendNodeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>会员推送通知节点修改结果</p>
 * @author Bob
 * @date 2020-01-13 10:47:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendNodeModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的会员推送通知节点信息
     */
    @Schema(description = "已修改的会员推送通知节点信息")
    private PushSendNodeVO pushSendNodeVO;
}
