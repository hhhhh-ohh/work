package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据网关id查询网关配置request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:34.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GatewayConfigByGatewayIdRequest extends PayBaseRequest {

    private static final long serialVersionUID = 2169959959027680461L;
    /**
     * 支付网关id
     */
    @Schema(description = "支付网关id")
    @NotNull
    private Long gatewayId;


    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}
