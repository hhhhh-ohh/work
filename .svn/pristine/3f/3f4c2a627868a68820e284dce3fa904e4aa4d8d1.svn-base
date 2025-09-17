package com.wanmi.sbc.goods.api.response.liveroomlivegoodsrel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.LiveRoomLiveGoodsRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）直播房间和直播商品关联表信息response</p>
 * @author zwb
 * @date 2020-06-08 09:12:17
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomLiveGoodsRelByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 直播房间和直播商品关联表信息
     */
    @Schema(description = "直播房间和直播商品关联表信息")
    private LiveRoomLiveGoodsRelVO liveRoomLiveGoodsRelVO;
}
