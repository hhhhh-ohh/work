package com.wanmi.sbc.message.api.response.pushsendnode;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.PushSendNodeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>会员推送通知节点分页结果</p>
 * @author Bob
 * @date 2020-01-13 10:47:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendNodePageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员推送通知节点分页结果
     */
    @Schema(description = "会员推送通知节点分页结果")
    private MicroServicePage<PushSendNodeVO> pushSendNodeVOPage;
}
