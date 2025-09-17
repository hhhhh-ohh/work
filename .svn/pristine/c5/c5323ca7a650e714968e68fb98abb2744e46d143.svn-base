package com.wanmi.sbc.goods.api.response.price.adjustment;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjustPriceExecuteResponse extends BasicResponse {

    private static final long serialVersionUID = -5804736324442295566L;

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    private List<String> skuIds;

    /**
     * 调价类型
     */
    @Schema(description = "调价类型")
    private PriceAdjustmentType type;
}
