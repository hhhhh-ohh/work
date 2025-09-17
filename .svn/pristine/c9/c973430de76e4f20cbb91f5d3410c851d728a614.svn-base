package com.wanmi.sbc.goods.api.response.livegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoLiveGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>直播商品分页结果</p>
 * @author zwb
 * @date 2020-06-06 18:49:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsPageNewResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 直播商品分页结果
     */
    @Schema(description = "直播商品分页结果")
    private MicroServicePage<LiveGoodsVO> liveGoodsVOPage;

    /**
     * 直播间所属店铺名字
     */
    @Schema(description = "直播间所属店铺名字")
    private Map<Long, String> storeName;

    /**
     * 规格信息
     */
    @Schema(description = "规格信息")
   private  List<GoodsInfoLiveGoodsVO> goodsInfoList;


}
