package com.wanmi.sbc.order.api.request.orderperformance;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class OrderPerformancePageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 系统唯一码
     */
    @NotBlank(message = "系统唯一码不可为空")
    @Schema(description = "系统唯一码")
    private String agentUniqueCode;


}
