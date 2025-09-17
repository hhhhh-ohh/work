package com.wanmi.sbc.goods.api.response.livegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>直播商品修改结果</p>
 * @author zwb
 * @date 2020-06-06 18:49:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的直播商品信息
     */
    @Schema(description = "已修改的直播商品信息")
    private LiveGoodsVO liveGoodsVO;
}
