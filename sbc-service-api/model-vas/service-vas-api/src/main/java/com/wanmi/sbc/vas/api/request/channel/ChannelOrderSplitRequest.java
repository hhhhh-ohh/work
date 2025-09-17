package com.wanmi.sbc.vas.api.request.channel;

import com.wanmi.sbc.vas.api.request.VasBaseRequest;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelOrderDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 渠道订单拆分请求类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderSplitRequest extends VasBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotNull
    @Schema(description = "供应商订单")
    private ChannelOrderDTO order;
}
