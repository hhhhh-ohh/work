package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 买家修改订单收货地址请求
 * @author malianfeng
 * @date 2022/11/17 14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeConsigneeModifyByBuyerRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @NotBlank
    private String tid;

    /**
     * 要更改的收货地址id
     */
    @Schema(description = "要更改的收货地址id")
    @NotBlank
    private String deliveryAddressId;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotBlank
    private String customerId;

    /**
     * 操作员
     */
    @Schema(description = "操作员")
    @NotNull
    private Operator operator;
}
