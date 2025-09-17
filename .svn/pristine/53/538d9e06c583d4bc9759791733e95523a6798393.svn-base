package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @Author: dyt
 * @Date: Created In 上午10:49 2020/4/21
 * @Description:
 */
@Schema
@Data
public class TradeItemConfirmModifyGoodsNumRequest extends BaseRequest {

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    @NotBlank
    private String goodsInfoId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @NotNull
    private Long buyCount;

    @Schema(description = "门店id")
    private Long storeId;
}
