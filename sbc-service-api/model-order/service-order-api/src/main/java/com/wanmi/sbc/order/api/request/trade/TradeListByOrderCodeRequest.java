package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据主订单号获取订单集合参数</p>
 * Created by of628-wenzhi on 2019-07-22-15:24.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeListByOrderCodeRequest extends BaseRequest {
    private static final long serialVersionUID = -9143745241530996245L;

    /**
     * 主交易单id
     */
    @NotNull
    @Schema(description = "主交易单id")
    private String tradeId;
}
