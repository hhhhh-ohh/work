package com.wanmi.sbc.order.orderperformance.model.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopSummaryDto {
    private Long areaId;
    private String areaName;
    private String shopName;
    private String season;
    private Long quantity;
    private BigDecimal commissionAmount;
    private Integer detailType;
}
