package com.wanmi.sbc.goods.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @Auther: jiaojiao
 * @Date: 2019/3/12 13:57
 * @Description:
 */
@Schema
@Data
public class GoodsEvaluateQueryGoodsAndStoreRequest extends BaseRequest {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /**
     * 货品id
     */
    @Schema(description = "货品id")
    @NotNull
    private String skuId;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    @NotNull
    private String tid;
}
