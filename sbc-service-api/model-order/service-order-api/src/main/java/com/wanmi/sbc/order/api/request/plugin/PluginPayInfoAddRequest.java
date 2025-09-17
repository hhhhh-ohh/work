package com.wanmi.sbc.order.api.request.plugin;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PluginPayInfoAddRequest extends BaseRequest {

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @NotBlank
    @Length(max=40)
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