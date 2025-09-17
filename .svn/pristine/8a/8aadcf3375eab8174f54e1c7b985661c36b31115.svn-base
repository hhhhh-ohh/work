package com.wanmi.sbc.customer.api.response.liveroom;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.LiveRoomVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>直播间列表结果</p>
 * @author zwb
 * @date 2020-06-06 18:28:57
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 直播间列表结果
     */
    @Schema(description = "直播间列表结果")
    private List<LiveRoomVO> liveRoomVOList;
}
