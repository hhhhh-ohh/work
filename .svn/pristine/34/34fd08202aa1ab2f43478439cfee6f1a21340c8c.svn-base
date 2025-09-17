package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wur
 * @className GetFreightInGoodsInfoRequest
 * @description TODO
 * @date 2022/7/6 9:23
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFreightInGoodsInfoRequest extends BaseQueryRequest {

    @Schema(description = "商品Id")
    @NotBlank
    private String goodsInfoId;

    @Schema(description = "价格")
    @NotNull
    private BigDecimal price;

    @Schema(description = "购买数量")
    @NotNull
    @Min(1)
    private Integer num = 1;

    @Schema(description = "秒杀商品Id")
    private Long flashSaleGoodsId;

    /**
     * 省级id
     */
    @Schema(description = "省级id")
    @NotBlank
    private String provinceId;

    /**
     * 城市id
     */
    @Schema(description = "城市id")
    @NotBlank
    private String cityId;

    /**
     * 区县id
     */
    @Schema(description = "区县id")
    @NotBlank
    private String areaId;

    /**
     * 街道id
     */
    @Schema(description = "街道id")
    private String streetId;

}