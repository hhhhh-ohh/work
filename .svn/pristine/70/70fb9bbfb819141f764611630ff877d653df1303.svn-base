package com.wanmi.sbc.order.api.request.flashsale;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @ClassName FlashSaleGoodsOrderCancelReturnStockRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/8/5 15:04
 **/
@Schema
@Data
public class FlashSaleGoodsOrderCancelReturnStockRequest extends BaseRequest {

    /**
     * 抢购商品Id
     */
    @Schema(description = "抢购商品Id")
    @NotNull
    private Long flashSaleGoodsId;

    /**
     * 抢购商品数量
     */
    @Schema(description = "抢购商品数量")
    private Integer flashSaleGoodsNum;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;
}
