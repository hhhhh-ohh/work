package com.wanmi.sbc.setting.api.request.stockWarning;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockWarningDeleteBySkuIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 商品skuId
     */
    @NotNull
    @Schema(description = "商品skuId")
    private String skuId;
}
