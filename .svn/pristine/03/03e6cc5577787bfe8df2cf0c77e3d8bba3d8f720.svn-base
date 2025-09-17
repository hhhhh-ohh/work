package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
*
 * @description  营销算价请求 - 订单商品信息
 * @author  wur
 * @date: 2022/2/24 10:37
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountPriceGoodsInfoDTO implements Serializable {
    private static final long serialversionuid = 1L;

    /**
     * SKU ID
     */
    @Schema(description = "SKUID")
    @NotBlank
    private String goodsInfoId;

    /**
     * SPU ID
     */
    @Schema(description = "SPUID")
    @NotBlank
    private String goodsId;

    /**
     * 市场价
     */
    @Schema(description = "价格")
    @NotNull
    private BigDecimal price;

    /**
     * 数量
     */
    @Schema(description = "数量")
    @Min(1L)
    private Long num;

    /**
     * 商品品牌
     */
    @Schema(description = "商品品牌")
    private Long brandId;

    @Schema(description = "分类")
    private Long cateId;

    @Schema(description = "店铺分类")
    private List<Long> storeCateIds;

    @Schema(description = "实付金额，当前商品在此业务节点的应收金额, 空则默认：price*num")
    private BigDecimal splitPrice;

    @Schema(description = "加价购商品价格占比")
    private Map<Long,BigDecimal> priceRate;

    public BigDecimal getSplitPrice() {
        return Objects.isNull(splitPrice) ? price.multiply(BigDecimal.valueOf(num)) : splitPrice;
    }

}