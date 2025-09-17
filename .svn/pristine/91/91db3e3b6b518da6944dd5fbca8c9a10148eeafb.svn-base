package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.empower.bean.enums.IsOpen;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>支付网关修改request</p>
 * Created by of628-wenzhi on 2018-08-20-下午2:53.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayModifyRequest extends GatewayAddRequest{

    private static final long serialVersionUID = 2419860462111303879L;
    /**
     * 是否开启: 0关闭 1开启
     */
    @Schema(description = "是否开启")
    private IsOpen isOpen = IsOpen.YES;

    @Schema(description = "支付网关id")
    @NotNull
    private Long id;
}
