package com.wanmi.sbc.order.api.request.plugin;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PluginPayInfoModifyRequest extends BaseRequest {

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderCode;

    /**
     * 支付请求
     */
    @Schema(description = "支付请求")
    private String payRequest;

    /**
     * 支付响应
     */
    @Schema(description = "支付响应")
    private String payResponse;

}