package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @ClassName FlashSaleGoodsBatchMinusStockRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/6/21 10:59
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsBatchMinusStockRequest extends BaseRequest {

    private static final long serialVersionUID = -5345359446184843648L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 扣减库存数
     */
    @Schema(description = "扣减库存数")
    @NotNull
    private Integer stock;
}
