package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGetAllCateRequest extends BaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /** 渠道类型 */
    @NotNull
    @Schema(description = "渠道类型")
    private ThirdPlatformType channelType;

}
