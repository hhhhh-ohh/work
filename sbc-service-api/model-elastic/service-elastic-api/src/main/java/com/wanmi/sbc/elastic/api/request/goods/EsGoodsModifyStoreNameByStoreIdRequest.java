package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Builder
public class EsGoodsModifyStoreNameByStoreIdRequest extends BaseRequest {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    @NotEmpty
    private String storeName;

    /**
     * 商户名称
     */
    @Schema(description = "商户名称")
    @NotEmpty
    private String supplierName;
}
