package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>新增支付网关request</p>
 * Created by of628-wenzhi on 2018-08-13-下午5:28.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayAddRequest extends PayBaseRequest {

    private static final long serialVersionUID = -6612649394605586668L;
    /**
     * 网关名称
     */
    @Schema(description = "网关名称")
    @NotNull
    private PayGatewayEnum gatewayEnum;

    /**
     * 是否聚合支付
     */
    @Schema(description = "是否聚合支付")
    @NotNull
    private Boolean type;

}
