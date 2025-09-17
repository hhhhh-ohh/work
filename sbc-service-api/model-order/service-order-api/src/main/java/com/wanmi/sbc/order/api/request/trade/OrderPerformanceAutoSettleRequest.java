package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class OrderPerformanceAutoSettleRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "天")
    private Integer day;
}
