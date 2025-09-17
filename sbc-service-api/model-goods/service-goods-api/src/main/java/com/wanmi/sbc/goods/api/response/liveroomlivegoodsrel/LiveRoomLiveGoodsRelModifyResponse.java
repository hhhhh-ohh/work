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
 * <p>直播房间和直播商品关联表修改结果</p>
 * @author zwb
 * @date 2020-06-08 09:12:17
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveRoomLiveGoodsRelModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的直播房间和直播商品关联表信息
     */
    @Schema(description = "已修改的直播房间和直播商品关联表信息")
    private LiveRoomLiveGoodsRelVO liveRoomLiveGoodsRelVO;
}
