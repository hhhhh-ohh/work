package com.wanmi.sbc.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description 购物车自动选券商品信息入参
 * @author malianfeng
 * @date 2022/5/26 16:43
 */
@Data
public class CouponSelectGoodsInfoDTO {

    @NotNull
    @Schema(description = "skuId")
    private String skuId;

    @NotNull
    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "加价购活动ID   如果加价购商品必传")
    private String preferentialActivityId;
}

