package com.wanmi.sbc.empower.api.request.channel.vop.order;

import com.wanmi.sbc.empower.api.request.vop.base.VopBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * 查询订单详情
 *
 * @author wur
 * @date: 2021/5/17 11:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class VopQueryOrderDetailRequest extends VopBaseRequest {

    private static final long serialVersionUID = -1L;

    /** 京东的订单单号 */
    @NotNull
    @Schema(description = "京东的订单单号(下单返回的父订单号)")
    private Long jdOrderId;

    /** 扩展参数 */
    @Schema(description = "扩展参数，支持多个状态组合查询[英文逗号间隔]，orderType:订单类型;jdOrderState；京东订单状态;name；收货人名称;address:收件人地址;mobile：手机号;poNo：采购单号;finishTime:订单完成时间;createOrderTime；订单创建时间;paymentType：订单支付类型;outTime：订单出库时间;invoiceType；订单发票类型;remark：备注")
    private String queryExts;
}
