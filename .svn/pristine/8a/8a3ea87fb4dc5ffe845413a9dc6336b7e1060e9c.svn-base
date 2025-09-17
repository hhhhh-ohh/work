package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-13
 */
@Schema
@Data
public class MarketingGoodsInfoDTO implements Serializable {

    private static final long serialVersionUID = 8676449137379428816L;

    /**
     * 分页商品SKU信息
     */
    @Schema(description = "分页商品SKU信息")
    private Page<GoodsInfoDTO> goodsInfoPage;

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
    private List<GoodsCateDTO> cates;
}
