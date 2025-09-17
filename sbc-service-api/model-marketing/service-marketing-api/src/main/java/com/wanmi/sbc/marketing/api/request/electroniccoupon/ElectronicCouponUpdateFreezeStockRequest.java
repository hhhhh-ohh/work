package com.wanmi.sbc.marketing.api.request.electroniccoupon;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新冻结库存request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ElectronicCouponUpdateFreezeStockRequest {

    /**
     * 电子卡券id
     */
    @Schema(description = "电子卡券id")
    @NotNull
    private Long id;

    /**
     * 冻结库存
     */
    @Schema(description = "冻结库存")
    @NotNull
    private Long freezeStock;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    private String orderNo;

    /**
     * 解除订单绑定
     */
    @Schema(description = "是否解除订单绑定")
    private Boolean unBindOrderFlag;
}
