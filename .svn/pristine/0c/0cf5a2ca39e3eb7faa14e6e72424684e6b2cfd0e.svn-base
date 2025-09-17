package com.wanmi.sbc.order.trade.model.root;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className PayingMemberInfo
 * @description 付费会员信息
 * @date 2022/5/17 2:11 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberInfo {

    /**
     * 付费会员等级id
     */
    private Integer levelId;

    /**
     * 付费记录id
     */
    private String recordId;

    /**
     * 商品优惠金额
     */
    private BigDecimal goodsDiscount;

    /**
     * 优惠券优惠金额
     */
    private BigDecimal couponDiscount;

    /**
     * 优惠金额：商品折扣优惠金额+优惠券优惠金额
     */
    private BigDecimal totalDiscount;
}
