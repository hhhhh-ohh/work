package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingSuitsBySkuIdRequest extends BaseRequest {


    /**
     * 商品SkuId
     */
    @Schema(description = "商品SkuId")
    @NotBlank
    private String  goodsInfoId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;
}
