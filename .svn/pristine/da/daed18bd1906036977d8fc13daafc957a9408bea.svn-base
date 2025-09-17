package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema
@Data
public class GoodsSuitsDTO implements Serializable {

    private static final long serialVersionUID = -8273793950120993783L;

    /**
     * 商品skuid
     */
    @Schema(description = "商品skuid")
    @NotNull
    private String goodsInfoId;

    /**
     * 折扣价 必填项
     */
    @Schema(description = "折扣价 必填项")
    @NotNull
    private BigDecimal discountPrice;


    /**
     * 数量
     */
    @Schema(description = "数量,必填")
    @NotNull
    @Max(999)
    private Long goodsSkuNum;



}
