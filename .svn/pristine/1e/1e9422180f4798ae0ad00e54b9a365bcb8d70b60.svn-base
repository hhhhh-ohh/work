package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsGoodsInfoAdjustPriceRequest extends BaseRequest {

    /**
     * skuId
     */
    @Schema(description = "skuId")
    @NotEmpty
    private List<String> goodsInfoIds;

    /**
     * 调价类型
     */
    @Schema(description = "调价类型")
    @NonNull
    private PriceAdjustmentType type;
}
