package com.wanmi.sbc.order.orderperformance.model.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonSummaryDto {
    private String season;
    private Long quantity;
    private BigDecimal uniformTotalAmount;
    private Integer detailType;
}
