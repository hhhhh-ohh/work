package com.wanmi.sbc.goods.api.request.adjustprice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceAdjustmentTemplateExportRequest extends BaseRequest {

    private static final long serialVersionUID = -3860545838217797275L;

    @Schema(description = "调价类型：0 市场价、 1 等级价、2 阶梯价、3 供货价")
    @NotNull
    private PriceAdjustmentType priceAdjustmentType;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
