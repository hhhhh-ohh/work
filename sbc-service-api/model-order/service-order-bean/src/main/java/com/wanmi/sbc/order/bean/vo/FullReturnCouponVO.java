package com.wanmi.sbc.order.bean.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 满返券
 * @author xufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullReturnCouponVO {

    /**
     * 券id
     */
    private String couponId;


    /**
     *  满返赠券Id
     */
    private Long returnDetailId;
}
