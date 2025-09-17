package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeDefaultPayRequest extends BaseRequest {

    /**
     * 交易id
     */
    @NotNull
    @Schema(description = "交易id")
    private String tid;

    @NotNull
    @Schema(description = "支付网关")
    private PayWay payWay;

}
