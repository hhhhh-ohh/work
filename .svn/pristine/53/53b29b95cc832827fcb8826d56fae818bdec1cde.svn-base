package com.wanmi.sbc.goods.api.request.adjustprice;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentResult;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AdjustPriceExecuteFailRequest extends GoodsBaseRequest {

    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    @NotBlank
    private String adjustNo;

    /**
     * 执行结果
     */
    @Schema(description = "执行结果")
    @NonNull
    private PriceAdjustmentResult result;

    /**
     * 失败原因
     */
    private String failReason;
}
