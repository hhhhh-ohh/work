package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.goods.bean.enums.PriceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * 商品级别价格实体
 * Created by dyt on 2017/4/17.
 */
@Schema
@Data
public class GoodsLevelPriceDTO implements Serializable {

    private static final long serialVersionUID = -1546601348463944114L;

    /**
     * 级别价格ID
     */
    @Schema(description = "级别价格ID")
    private Long levelPriceId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 等级ID
     */
    @Schema(description = "等级ID")
    private Long levelId;

    /**
     * 等级名称
     */
    @Schema(description = "等级名称")
    private String levelName;

    /**
     * 订货价
     */
    @Schema(description = "订货价")
    private BigDecimal price;

    /**
     * 起订量
     */
    @Schema(description = "起订量")
    private Long count;

    /**
     * 限订量
     */
    @Schema(description = "限订量")
    private Long maxCount;

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
