package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FlashSaleGoodsBatchStockAndSalesVolumeRequest
 * @author lvzhenwei
 * @date 2019/6/22 10:10
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsBatchStockAndSalesVolumeRequest extends BaseRequest {

    private static final long serialVersionUID = -8654401141330825540L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    @NotNull
    private Integer num;
}
