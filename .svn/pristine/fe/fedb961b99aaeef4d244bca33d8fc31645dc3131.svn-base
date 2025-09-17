package com.wanmi.sbc.order.api.request.paytraderecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>根据支付/退款对象id获取交易记录数request</p>
 * Created by of628-wenzhi on 2018-08-13-下午3:53.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeRecordCountByOrderCodeRequest extends OrderBaseRequest {

    private static final long serialVersionUID = 6033400669527184587L;
    /**
     * 业务单号（订单号/退单号）
     */
    @Schema(description = "业务单号（订单号/退单号）")
    @NotBlank
    private String orderId;
}
