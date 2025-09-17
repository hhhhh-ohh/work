package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端采购单缓存中的商品DTO
 */
@Data
@Schema
public class PurchaseGoodsInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * SkuId
     */
    @Schema(description = "SkuId", required = true)
    @NotNull
    private String goodsInfoId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量", required = true)
    @NotNull
    @Min(1)
    private Long goodsNum;
}
