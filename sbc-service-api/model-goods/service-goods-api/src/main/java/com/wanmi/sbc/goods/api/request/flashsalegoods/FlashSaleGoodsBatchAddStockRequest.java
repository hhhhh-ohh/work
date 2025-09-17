package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @ClassName FlashSaleGoodsBatchAddStockRequest
 * @Description 增加秒杀商品活动对应的商品库存
 * @Author lvzhenwei
 * @Date 2019/7/1 16:01
 **/
@Data
public class FlashSaleGoodsBatchAddStockRequest extends BaseRequest {

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 增加库存数
     */
    @Schema(description = "增加库存数")
    @NotNull
    private Integer stock;
}
