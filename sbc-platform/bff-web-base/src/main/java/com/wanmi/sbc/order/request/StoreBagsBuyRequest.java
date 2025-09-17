package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:28 2019/3/6
 * @Description: 开店礼包购买请求
 */
@Schema
@Data
public class StoreBagsBuyRequest extends BaseRequest {

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    @NotNull
    private String goodsInfoId;

}
