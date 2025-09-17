package com.wanmi.sbc.customer.api.response.liveroom;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.LiveRoomVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>直播间分页结果</p>
 * @author zwb
 * @date 2020-06-06 18:28:57
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 直播间分页结果
     */
    @Schema(description = "直播间分页结果")
    private MicroServicePage<LiveRoomVO> liveRoomVOPage;


}
