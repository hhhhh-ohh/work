package com.wanmi.sbc.elastic.bean.dto.goods;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 前端采购单缓存中的商品DTO
 */
@Data
@Schema
public class EsGoodsInfoDTO {

    /**
     * SkuId
     */
    @Schema(description = "SkuId")
    @NotNull
    private String goodsInfoId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @NotNull
    private Long goodsNum;
}
