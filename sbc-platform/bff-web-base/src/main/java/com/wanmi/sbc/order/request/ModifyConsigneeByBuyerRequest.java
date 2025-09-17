package com.wanmi.sbc.order.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

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
public class ModifyConsigneeByBuyerRequest implements Serializable {

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
}
