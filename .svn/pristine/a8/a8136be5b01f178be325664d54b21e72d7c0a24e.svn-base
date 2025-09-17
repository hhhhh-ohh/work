package com.wanmi.sbc.order.api.request.linkedmall;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * linkedMall申请请求结构
 * Created by dyt on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdPlatformReturnOrderSyncRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方平台类型
     */
    @Schema(description = "第三方平台类型")
    @NotNull
    private ThirdPlatformType thirdPlatformType;
}
