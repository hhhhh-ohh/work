package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品批量改价阶梯价格实体
 * Created by dyt on 2017/4/17.
 */
@Schema
@Data
public class GoodsAdjustIntervalPriceVO extends BasicResponse {

    /**
     * 订货区间ID
     */
    @Schema(description = "订货区间ID")
    private Long intervalPriceId;

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
    private String goodsId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsInfoId;
}
