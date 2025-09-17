package com.wanmi.sbc.empower.api.request.channel.logistics;


import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className LogisticsQueryRequest
 * @description
 * @author    张文昌
 * @date      2021/5/29 17:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ChannelLogisticsQueryRequest extends BaseRequest {

    @Schema(description = "第三方主订单id", required = true)
    @NotNull
    private Long orderId;

    @Schema(description = "商城内部用户id")
    private String bizUid;

    @Schema(description = "第三方平台类型", required = true)
    @NotNull
    private ThirdPlatformType thirdPlatformType;
}
