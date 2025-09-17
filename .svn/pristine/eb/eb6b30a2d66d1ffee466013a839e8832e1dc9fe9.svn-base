package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品SKU库存减量请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoMinusStockByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 7877938261086175936L;

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    @NotBlank
    private String goodsInfoId;

    /**
     * 库存减量
     */
    @Schema(description = "库存减量")
    @NotNull
    private Long stock;
//
//
//    @Schema(description = "spuId")
//    @NotNull
//    private String goodsId;
}
