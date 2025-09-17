package com.wanmi.sbc.order.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class OrderTodoResp implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 待付款
     */
    @Schema(description = "待付款")
    private Long waitPay;

    /**
     * 待发货
     */
    @Schema(description = "待发货")
    private Long waitDeliver;

    /**
     * 待收货
     */
    @Schema(description = "待收货")
    private Long waitReceiving;

    /**
     * 待评价
     */
    @Schema(description = "待评价")
    private Long waitEvaluate;

    /**
     * 退货、退款
     */
    @Schema(description = "退货、退款")
    private Long refund;


}