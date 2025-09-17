package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Autho qiaokang
 * @Date：2019-03-14 19:24:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeUpdateCommissionFlagRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 交易号
     */
    @Schema(description = "交易号")
    @NotBlank
    private String tradeId;

    /**
     * 是否返利
     */
    @Schema(description = "是否返利")
    @NotNull
    private Boolean commissionFlag;

}
