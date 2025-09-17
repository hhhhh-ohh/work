package com.wanmi.sbc.order.api.request.paytraderecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>根据交易单号获取单笔交易记录request</p>
 * Created by of628-wenzhi on 2018-08-13-下午3:53.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeRecordByOrderCodeRequest extends OrderBaseRequest {

    private static final long serialVersionUID = -6803452665641566819L;
    /**
     * 订单/退单号
     */
    @Schema(description = "订单/退单号")
    @NotBlank
    private String orderId;
}
