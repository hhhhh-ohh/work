package com.wanmi.sbc.customer.api.response.liveroom;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.LiveGoodsByWeChatVO;
import com.wanmi.sbc.customer.bean.vo.LiveRoomReplayVO;
import com.wanmi.sbc.customer.bean.vo.LiveRoomVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
public class LiveRoomPageMobileResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 直播间分页结果
     */
    @Schema(description = "直播间分页结果")
    private MicroServicePage<LiveRoomVO> liveRoomVOPage;

    /**
     * 直播回放结果
     */
    @Schema(description = "直播回放结果")
    private  Map<Long, List<LiveRoomReplayVO>> LiveRoomReplayVOList;

    /**
     * 直播间所属店铺名字
     */
    @Schema(description = "直播间所属店铺名字")
    private  Map<Long, StoreVO> storeVO;

    /**
     * 直播间商品
     */
    @Schema(description = "直播间商品")
    private   Map<Long, List<LiveGoodsByWeChatVO>> liveGoodsList;


    /**
     * 直播中的数量
     */
    @Schema(description = "直播中的数量")
    private  Long liveCount;

    /**
     * 直播预告数量
     */
    @Schema(description = "直播预告数量")
    private  Long foreShowCount;

    /**
     * 直播回放房间数量
     */
    @Schema(description = "直播回放房间数量")
    private  Long playbackCount;

}
