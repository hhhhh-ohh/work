package com.wanmi.sbc.goods.api.request.adjustprice;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>执行调价任务请求参数</p>
 * Created by of628-wenzhi on 2020-12-17-11:54 上午.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AdjustPriceExecuteRequest extends GoodsBaseRequest {

    private static final long serialVersionUID = 1685872077872878775L;
    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    @NotBlank
    private String adjustNo;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotNull
    private Long storeId;

}
