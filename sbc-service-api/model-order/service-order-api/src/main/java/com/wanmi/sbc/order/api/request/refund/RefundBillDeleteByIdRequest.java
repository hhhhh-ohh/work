package com.wanmi.sbc.order.api.request.refund;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 流水单
 * Created by zhangjin on 2017/4/21.
 */
@Data
@Schema
public class RefundBillDeleteByIdRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "退款id")
    private String refundId;
}
