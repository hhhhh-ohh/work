package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 渠道订单确认收货请求类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderConfirmReceivedRequest extends BaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotNull
    @Schema(description = "渠道平台订单id")
    private String channelOrderId;

    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;
}
