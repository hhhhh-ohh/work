package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品SKU视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponGoodsVO extends BasicResponse {

    private static final long serialVersionUID = -2310808028751250150L;

    /**
     * 分页商品SKU信息
     */
    @Schema(description = "商品SKU信息分页")
    private MicroServicePage<GoodsInfoVO> goodsInfoPage;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息列表")
    private List<GoodsInfoVO> goodsInfos;

    /**
     * 商品SPU信息
     */
    @Schema(description = "商品SPU信息列表")
    private List<GoodsVO> goodses;

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    /**
     * 品牌列表
     */
    @Schema(description = "商品品牌列表")
    private List<GoodsBrandVO> brands;

    /**
     * 分类列表
     */
    @Schema(description = "商品分类列表")
    private List<GoodsCateVO> cates;
}
