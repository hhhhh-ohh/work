package com.wanmi.sbc.vas.api.request.sellplatform.order;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description SellPlatformOrderRequest
 * @author wur
 * @date: 2022/4/20 9:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformOrderRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotEmpty
    @Schema(description = "用户关联的OpenId")
    private String thirdOpenId;

    @Schema(description = "订单Id")
    private String orderId;

    @Schema(description = "代销平台订单号")
    private String sellOrderId;

}
