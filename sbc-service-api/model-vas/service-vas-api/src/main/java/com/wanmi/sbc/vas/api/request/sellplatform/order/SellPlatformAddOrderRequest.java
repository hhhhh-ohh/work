package com.wanmi.sbc.vas.api.request.sellplatform.order;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description SellPlatformAddOrderRequest
 * @author wur
 * @date: 2022/4/20 9:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformAddOrderRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotEmpty
    @Schema(description = "用户关联的OpenId")
    private String thirdOpenId;

    @NotNull
    @Schema(description = "订单信息")
    private SellPlatformTradeVO tradeVO;

}
