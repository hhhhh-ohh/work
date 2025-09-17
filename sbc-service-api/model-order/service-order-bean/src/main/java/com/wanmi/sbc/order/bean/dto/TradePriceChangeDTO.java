package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.math.BigDecimal;

/**
 * <p>订单改价请求参数结构</p>
 * Created by of628-wenzhi on 2018-05-31-下午3:25.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradePriceChangeDTO extends BaseRequest{

    /**
     * 订单总价
     */
    @Schema(description = "订单总价", required = true)
    @NotNull
    @Min(0L)
    private BigDecimal totalPrice;

    @Schema(description = "运费")
    @Min(0L)
    private BigDecimal freight;
}
