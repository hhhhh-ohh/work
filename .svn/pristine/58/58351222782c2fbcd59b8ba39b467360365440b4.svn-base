package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  查询代销商品信息
 * @author  wur
 * @date: 2022/7/28 15:07
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsSellRequest extends GoodsBaseRequest {

    private static final long serialVersionUID = 5621833007962215905L;
    /**
     * 代销商品Id
     */
    @Schema(description = "代销商品Id")
    @NotEmpty
    private String providerGoodsId;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    @NotNull
    private Long storeId;
}
