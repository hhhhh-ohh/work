package com.wanmi.sbc.order.api.request.thirdplatformreturn;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 第三方子订单自动申请退单
 * Created by dyt on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdPlatformReturnOrderAutoApplySubRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "第三方渠道订单id")
    @NotBlank
    private String thirdPlatformSubTradeId;

    @Schema(description = "退款原因")
    @NotBlank
    private String description;
}
