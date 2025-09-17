package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 代销商品可售参数
 * 1、更改spu时，只需传入spuId
 * 2、只更改sku，不影响spu的可售状态时只需传入skuId
 * 3、如果更改sku，同时更改了spu的可售状态时，spuId和skuId都需要
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Builder
public class ProviderGoodsNotSellRequest extends BaseRequest {

    /**
     * spuIds
     */
    @Schema(description = "spuIds")
    private List<String> goodsIds = new ArrayList<>();

    /**
     * skuIds
     */
    @Schema(description = "skuIds")
    private List<String> goodsInfoIds = new ArrayList<>();

    /**
     * 是否需要根据商品状态（上架、删除、审核）校验
     */
    @NotNull
    private Boolean checkFlag;

    /**
     * 是否需要更新库存
     */
    private Boolean stockFlag;


}
