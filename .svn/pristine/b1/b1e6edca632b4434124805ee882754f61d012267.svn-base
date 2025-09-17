package com.wanmi.sbc.elastic.bean.vo.goods;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.goods.bean.enums.PriceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品客户价格实体
 * Created by dyt on 2017/4/17.
 */
@Data
@Schema
public class GoodsCustomerPriceNestVO extends BasicResponse {

    /**
     * 客户价格ID
     */
    @Schema(description = "客户价格ID")
    private Long customerPriceId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

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
    @Schema(description = "类型")
    private PriceType type;

}
