package com.wanmi.sbc.order.api.request.paytraderecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>根据订单号查询支付结果请求参数</p>
 * Created by of628-wenzhi on 2018-08-18-下午2:31.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayResultByOrdercodeRequest extends OrderBaseRequest {
    
    private static final long serialVersionUID = 8893296809651806348L;
    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    private String orderCode;
}
