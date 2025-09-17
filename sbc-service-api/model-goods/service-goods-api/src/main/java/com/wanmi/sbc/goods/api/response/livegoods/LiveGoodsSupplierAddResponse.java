package com.wanmi.sbc.goods.api.response.livegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.LiveGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>直播商品新增结果</p>
 * @author zwb
 * @date 2020-06-06 18:49:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsSupplierAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品列表
     */
    @Schema(description = "商品列表")
    private List<LiveGoodsVO> goodsInfoVOList;

}
