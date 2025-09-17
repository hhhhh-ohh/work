package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 获取订单商品详情响应
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeGoodsListVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<GoodsInfoVO> goodsInfos;

    /**
     * 商品SPU信息
     */
    @Schema(description = "商品SPU信息")
    private List<GoodsVO> goodses;

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 品牌列表
     */
    @Schema(description = "品牌列表")
    private List<GoodsBrandVO> brands;

    /**
     * 分类列表
     */
    @Schema(description = "分类列表")
    private List<GoodsCateVO> cates;
}
