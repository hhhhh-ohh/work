package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>根据团号获取订单集合参数</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeListByGrouponNoRequest extends BaseRequest {
    private static final long serialVersionUID = -9106216529628861802L;

    /**
     * 团编号
     */
    @NotNull
    @Schema(description = "团编号")
    private String grouponNo;

}
