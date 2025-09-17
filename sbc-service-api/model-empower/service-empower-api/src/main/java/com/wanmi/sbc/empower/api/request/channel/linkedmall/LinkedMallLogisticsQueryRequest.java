package com.wanmi.sbc.empower.api.request.channel.linkedmall;


import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * linkedmall 查询订单物流请求
 * \* User: yhy
 * \* Date: 2020-8-12
 * \* Time: 17:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LinkedMallLogisticsQueryRequest extends BaseRequest {

    @Schema(description = "linkedmall 主订单id")
    @NotNull
    private Long lmOrderId;

    @Schema(description = "商城内部用户id")
    @NotBlank
    private String bizUid;

}
