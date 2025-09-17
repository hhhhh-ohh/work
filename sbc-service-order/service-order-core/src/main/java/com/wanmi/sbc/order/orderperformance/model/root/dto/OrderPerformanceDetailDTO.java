package com.wanmi.sbc.order.orderperformance.model.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class OrderPerformanceDetailDTO {
    /**
     * 明细类型（1-购买商品，2-退货商品）
     */
    private Integer detailType;

    /**
     * 数量
     */
    private Integer quantity;


    /**
     * 佣金金额
     */
    private BigDecimal commissionAmount;

    /**
     * 校服商品总价
     */
    private BigDecimal uniformTotalAmount;

    /**
     * 季节
     */
    private String season;
}
