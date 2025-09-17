package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.dto.*;
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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeGoodsInfoPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分页商品SKU信息
     */
    @Schema(description = "分页商品SKU信息")
    private MicroServicePage<GoodsInfoDTO> goodsInfoPage;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<GoodsInfoDTO> goodsInfos;

    /**
     * 商品SPU信息
     */
    @Schema(description = "商品SPU信息")
    private List<GoodsDTO> goodses;

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceDTO> goodsIntervalPrices;

    /**
     * 品牌列表
     */
    @Schema(description = "品牌列表")
    private List<GoodsBrandDTO> brands;

    /**
     * 分类列表
     */
    @Schema(description = "分类列表")
    private List<GoodsCateVO> cates;
}
