package com.wanmi.sbc.order.api.request.payorder;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class FindPayOrderByOrderCodesRequest extends BaseRequest {

    @Schema(description = "订单编号")
    private List<String> value;
}
