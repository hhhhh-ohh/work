package com.wanmi.sbc.empower.api.request.pay.channelItem;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据支付网关id获取单个支付渠道项request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:11.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelItemByGatewayRequest extends PayBaseRequest {

    private static final long serialVersionUID = -284889703450998452L;
    /**
     * 支付网关类型
     */
    @Schema(description = "支付网关类型")
    @NotNull
    private PayGatewayEnum gatewayName;
}
