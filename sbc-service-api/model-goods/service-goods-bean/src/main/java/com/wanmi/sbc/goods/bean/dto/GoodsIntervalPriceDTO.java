package com.wanmi.sbc.goods.bean.dto;


import com.wanmi.sbc.goods.bean.enums.PriceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品订货区间价格实体
 * Created by dyt on 2017/4/17.
 */
@Schema
@Data
public class GoodsIntervalPriceDTO implements Serializable {

    /**
     * 订货区间ID
     */
    @Schema(description = "订货区间ID")
    private Long intervalPriceId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsId;

    /**
     * 订货区间
     */
    @Schema(description = "订货区间")
    private Long count;

    /**
     * 订货价
     */
    @Schema(description = "订货价")
    private BigDecimal price;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsInfoId;

    /**
     * 类型
     */
    @Schema(description = "类型，0：spu数据 1sku数据")
    private PriceType type;
}
