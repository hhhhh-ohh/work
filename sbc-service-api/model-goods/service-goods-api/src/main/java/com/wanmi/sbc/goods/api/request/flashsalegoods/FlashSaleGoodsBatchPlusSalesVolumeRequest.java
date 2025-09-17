package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FlashSaleGoodsBatchPlusStockRequest
 * @author lvzhenwei
 * @date 2019/6/22 9:43
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsBatchPlusSalesVolumeRequest extends BaseRequest {

    private static final long serialVersionUID = -3163866975944361563L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 增加销量数
     */
    @Schema(description = "增加销量数")
    @NotNull
    private Integer salesVolume;
}
