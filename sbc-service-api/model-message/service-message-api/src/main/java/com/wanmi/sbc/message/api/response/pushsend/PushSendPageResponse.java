package com.wanmi.sbc.message.api.response.pushsend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.PushSendVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>会员推送信息分页结果</p>
 * @author Bob
 * @date 2020-01-08 17:15:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员推送信息分页结果
     */
    @Schema(description = "会员推送信息分页结果")
    private MicroServicePage<PushSendVO> pushSendVOPage;
}
