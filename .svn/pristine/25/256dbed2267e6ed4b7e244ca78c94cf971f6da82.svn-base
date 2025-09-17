package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsGoodsInfoDetailVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 营销活动Id
     */
    @Schema(description = "营销活动Id")
    private Long marketingId;

    /**
     * 组合套餐名称
     */
    @Schema(description = "组合套餐名称")
    private String marketingName;

    /**
     * 套餐主图
     */
    @Schema(description = "套餐主图")
    private String mainImage;

    /**
     * 套餐价
     */
    @Schema(description = "套餐价")
    private BigDecimal suitsPrice;

    /**
     * 组合套餐商品最高省
     */
    @Schema(description = "组合套餐商品最高省")
    private BigDecimal suitsNoNeedPrice;

}
