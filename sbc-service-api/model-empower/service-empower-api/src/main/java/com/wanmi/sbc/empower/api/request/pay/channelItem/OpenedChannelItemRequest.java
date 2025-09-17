package com.wanmi.sbc.empower.api.request.pay.channelItem;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>获取当前终端对应网关下已开启的支付渠道项request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:15.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenedChannelItemRequest extends PayBaseRequest {

    private static final long serialVersionUID = 4273948357612475767L;
    /**
     * 支付网关名称
     */
    @Schema(description = "支付网关名称")
    @NotNull
    private PayGatewayEnum gatewayName;

    /**
     * 终端类型
     */
    @Schema(description = "终端类型")
    @NotNull
    private TerminalType terminalType;
}
