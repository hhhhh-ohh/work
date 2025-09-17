package com.wanmi.sbc.coupon.dto;

import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.List;

/**
 * @description 购物车自动选券商品信息入参
 * @author malianfeng
 * @date 2022/5/26 16:43
 */
@Data
public class CouponCheckoutTradeParams {

    @Schema(description = "旧订单商品数据，用于编辑订单的场景")
    private List<TradeItemDTO> oldTradeItems;

    @Schema(description = "加价购商品订单")
    private List<TradeItemDTO> oldPreferential;
}

