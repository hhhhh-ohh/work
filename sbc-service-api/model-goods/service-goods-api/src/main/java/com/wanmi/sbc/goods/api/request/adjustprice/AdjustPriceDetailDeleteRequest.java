package com.wanmi.sbc.goods.api.request.adjustprice;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>删除调价详情请求参数</p>
 * Created by of628-wenzhi on 2020-12-15-7:33 下午.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AdjustPriceDetailDeleteRequest extends GoodsBaseRequest {
    private static final long serialVersionUID = -1678822142894757657L;

    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    @NotBlank
    private String adjustNo;

    /**
     * 调价明细ID
     */
    @Schema(description = "调价明细id")
    @NonNull
    private Long adjustDetailId;
}
