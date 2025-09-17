package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据id查询单个支付网关配置request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:27.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayConfigByIdRequest extends PayBaseRequest {

    private static final long serialVersionUID = -6951199247900125276L;
    /**
     * 支付网关配置id
     */
    @Schema(description = "支付网关配置id")
    @NotNull
    private Long gatewayConfigId;
}
