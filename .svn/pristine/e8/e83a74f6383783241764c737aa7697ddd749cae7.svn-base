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
 * 商品SKU库存增量请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoPlusStockByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -6433911700969011365L;

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    @NotBlank
    private String goodsInfoId;

    /**
     * 库存增量
     */
    @Schema(description = "库存增量")
    @NotNull
    private Long stock;
}
