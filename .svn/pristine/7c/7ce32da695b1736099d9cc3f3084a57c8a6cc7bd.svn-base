package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsGoodsInitProviderGoodsInfoRequest extends BaseRequest {

    /**
     * 供应商关联的商家商品id
     */
    @Schema(description = "供应商关联的商家商品id")
    private List<String> providerGoodsIds;

    @Schema(description = "店铺Id")
    private Long storeId;

}
