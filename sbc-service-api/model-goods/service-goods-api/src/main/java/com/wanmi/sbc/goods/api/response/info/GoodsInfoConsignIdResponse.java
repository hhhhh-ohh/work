package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsSpecDetailVO;
import com.wanmi.sbc.goods.bean.vo.GoodsSpecVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsInfoConsignIdResponse extends BasicResponse {
    private static final long serialVersionUID = 712116094839998228L;

    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private GoodsVO goods;


    /**
     * 商品SKU列表
     */
    @Schema(description = "商品SKU")
    private GoodsInfoVO goodsInfos;

    /**
     * 商品规格列表
     */
    @Schema(description = "商品规格列表")
    private GoodsSpecVO goodsSpecs;


    /**
     * 商品规格值列表
     */
    @Schema(description = "商品规格值列表")
    private GoodsSpecDetailVO goodsSpecDetails;
}
