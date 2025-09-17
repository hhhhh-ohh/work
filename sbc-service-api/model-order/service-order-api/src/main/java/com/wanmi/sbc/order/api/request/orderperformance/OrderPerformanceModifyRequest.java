package com.wanmi.sbc.order.api.request.orderperformance;


import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPerformanceModifyRequest extends BaseRequest {


    /**
     * 订单号
     */
    @NotNull(message = "订单号不可为空")
    @Schema(description = "订单号")
    private String id;


}
