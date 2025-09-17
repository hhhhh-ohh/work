package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelTradeDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 渠道订单创建请求类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderCreateRequest extends BaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @Valid
    @NotNull
    @Schema(description = "订单信息")
    private ChannelTradeDTO channelTrade;
}
