package com.wanmi.sbc.order.api.request.payorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.PayOrderDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class DestoryPayOrderRequest extends BaseRequest {

    @Schema(description = "付款单列表")
    List<PayOrderDTO> payOrders;

    /**
     * 操作员
     */
    @Schema(description = "操作员")
    private Operator operator;
}
