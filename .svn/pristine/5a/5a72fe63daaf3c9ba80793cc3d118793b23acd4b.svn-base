package com.wanmi.sbc.returnorder.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 退款金额
 * Created by jinwei on 25/4/2017.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPriceRequest extends BaseRequest {

    /**
     * 申请金额状态，是否启用
     */
    @Schema(description = "申请金额状态，是否启用",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private Boolean applyStatus;

    /**
     * 申请金额
     */
    @Schema(description = "申请金额")
    private BigDecimal applyPrice;

    /**
     * 退款总额
     */
    @Schema(description = "退款总额")
    private BigDecimal totalPrice;
}
