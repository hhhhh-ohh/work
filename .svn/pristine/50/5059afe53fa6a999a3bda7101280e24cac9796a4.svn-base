package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.PriceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品订货区间价格实体
 *
 * @author dyt
 * @date 2017/4/17
 */
@Schema
@Data
public class GoodsIntervalPriceVO extends BasicResponse {

    private static final long serialVersionUID = 712099070869600939L;

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
