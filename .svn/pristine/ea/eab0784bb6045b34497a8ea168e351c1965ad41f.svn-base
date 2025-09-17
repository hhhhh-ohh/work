package com.wanmi.sbc.order.api.request.refund;

import com.wanmi.sbc.common.base.BaseRequest;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class RefundOrderRefuseRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "退单id")
    private String id;

    @Schema(description = "退款原因")
    private String refuseReason;
}
