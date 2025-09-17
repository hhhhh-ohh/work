package com.wanmi.sbc.order.api.request.refund;

import com.wanmi.sbc.common.base.BaseRequest;

import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class RefundOrderRefundRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "退单id")
    private String rid;

    @Schema(description = "退款失败原因")
    private String failedReason;

    @Schema(description = "操作人")
    private Operator operator;
}
