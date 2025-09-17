package com.wanmi.sbc.order.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 消费记录修改请求
 * @Autho qiaokang
 * @Date：2019-03-05 18:44:58
 */
@Data
@Schema
public class ConsumeRecordModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @NotNull
    private String orderId;

    /**
     * 有效消费额
     */
    @Schema(description = "有效消费额")
    @NotNull
    private BigDecimal validConsumeSum;

    @Schema(description = "订单状态")
    private String flowState;

}
