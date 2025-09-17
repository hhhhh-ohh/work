package com.wanmi.sbc.order.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: sbc-micro-service-B
 * @description:
 * @create: 2020-07-16 15:51
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class OrderCommissionResponse implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 订单佣金
     */
    @Schema(description = "订单佣金")
    private BigDecimal commission;


}