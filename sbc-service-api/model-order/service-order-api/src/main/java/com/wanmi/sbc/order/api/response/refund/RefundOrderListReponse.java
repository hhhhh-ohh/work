package com.wanmi.sbc.order.api.response.refund;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundOrderListReponse extends BasicResponse {

    /**
     * 退款记录列表
     */
    @Schema(description = "退款记录列表")
    private List<RefundOrderResponse> refundOrderResponseList;
}
