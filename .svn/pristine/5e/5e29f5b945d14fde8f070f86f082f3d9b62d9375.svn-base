package com.wanmi.sbc.order.returnorder.model.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className ReturnTag
 * @description 退单标记
 * @date 2022/2/17 1:53 下午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnTag {

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
}
