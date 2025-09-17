package com.wanmi.sbc.order.trade.model.root;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单标记
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderTag {

    /**
     * 是否是虚拟订单
     */
    private Boolean virtualFlag = Boolean.FALSE;


    /**
     * 是否是卡券订单
     */
    private Boolean electronicCouponFlag = Boolean.FALSE;


    /**
     * 是否是周期购订单
     */
    @Builder.Default
    private Boolean buyCycleFlag = Boolean.FALSE;

    /**
     * 提货卡订单
     */
    @Builder.Default
    private Boolean pickupCardFlag = Boolean.FALSE;

    /**
     * 社区团购订单
     */
    @Builder.Default
    private Boolean communityFlag = Boolean.FALSE;
}
