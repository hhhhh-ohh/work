package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ChannelRefundQueryStatusRequest extends BaseRequest {

    @NotNull
    @Schema(description = "渠道服务类型")
    private ThirdPlatformType thirdPlatformType;

    @ApiEnumProperty("发起退款的渠道子订单ID")
    @NotBlank
    private String subChannelOrderId;

    @Schema(description = "用户id")
    @NotBlank
    private String bizUid;
}
