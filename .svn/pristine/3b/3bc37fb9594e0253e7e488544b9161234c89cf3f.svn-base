package com.wanmi.sbc.order.api.request.paytraderecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>根据支付/退款对象id获取单笔交易记录request</p>
 * Created by of628-wenzhi on 2018-08-13-下午3:53.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeRecordByChargeRequest extends OrderBaseRequest {

    private static final long serialVersionUID = -6174445704791404533L;
    /**
     * 支付/退款对象id
     */
    @Schema(description = "支付/退款对象id")
    @NotBlank
    private String chargeId;
}
