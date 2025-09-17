package com.wanmi.sbc.customer.api.response.liveroomreplay;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.LiveRoomReplayVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>直播回放分页结果</p>
 * @author zwb
 * @date 2020-06-17 09:24:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomReplayPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 直播回放分页结果
     */
    @Schema(description = "直播回放分页结果")
    private MicroServicePage<LiveRoomReplayVO> liveRoomReplayVOPage;
}
