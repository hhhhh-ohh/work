package com.wanmi.sbc.customer.api.response.liveroomreplay;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.LiveRoomReplayVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>直播回放修改结果</p>
 * @author zwb
 * @date 2020-06-17 09:24:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomReplayModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的直播回放信息
     */
    @Schema(description = "已修改的直播回放信息")
    private LiveRoomReplayVO liveRoomReplayVO;
}
