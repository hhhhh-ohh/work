package com.wanmi.sbc.message.api.response.appmessage;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.AppMessageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>App站内信消息发送表分页结果</p>
 * @author xuyunpeng
 * @date 2020-01-06 10:53:00
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppMessagePageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * App站内信消息发送表分页结果
     */
    @Schema(description = "App站内信消息发送表分页结果")
    private MicroServicePage<AppMessageVO> appMessageVOPage;

    @Schema(description = "服务通知消息数量（未读）")
    private Integer NoticeNum;

    @Schema(description = "优惠促销消息数量（未读）")
    private Integer PreferentialNum;
}
