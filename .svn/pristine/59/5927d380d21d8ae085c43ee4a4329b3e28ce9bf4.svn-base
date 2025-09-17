package com.wanmi.sbc.account.finance.record.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>对账总计结构</p>
 * Created by of628-wenzhi on 2017-12-07-下午7:34.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalRecord {

    /**
     * 商家id
     */
    private Long supplierId;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 总计
     */
    private BigDecimal totalAmount;

    /**
     * 积分数量，包含下单的积分抵扣、商品积分抵扣、积分兑换
     */
    private BigDecimal totalPoints;

    /**
     * 积分抵现金额，包含下单的积分抵扣、商品积分抵扣、积分兑换
     */
    private BigDecimal totalPointsPrice;

    /**
     * 礼品卡抵扣金额
     */
    private BigDecimal totalGiftCardPrice;


}
