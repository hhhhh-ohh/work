package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据支付网关枚举获取网关配置request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:30.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayConfigByGatewayRequest extends PayBaseRequest {

    private static final long serialVersionUID = -8710173689350358682L;
    /**
     * 支付网关枚举
     */
    @Schema(description = "支付网关枚举")
    @NotNull
    private PayGatewayEnum gatewayEnum;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
