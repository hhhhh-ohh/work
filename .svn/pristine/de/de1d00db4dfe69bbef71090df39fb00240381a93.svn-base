package com.wanmi.sbc.order.trade.model.entity.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wur
 * @className Freight
 * @description 供应商运费信息
 * @date 2021/9/9 14:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProviderFreight implements Serializable {

    /**
     * 商家运费
     */
    private BigDecimal supplierFreight;

    /**
     * 供应商ID
     */
    private Long providerId;

    /**
     * 运费承担者  0.买家 1.卖家 2.卖家部分承担
     */
    private Integer bearFreight;

    /**
     * 商家承担的运费 当使用运费券是 此字段可能会有数据
     */
    private BigDecimal supplierBearFreight;

}