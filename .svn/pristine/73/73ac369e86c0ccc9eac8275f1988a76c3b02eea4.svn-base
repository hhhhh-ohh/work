package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.RefundOrderDTO;
import com.wanmi.sbc.order.bean.dto.ReturnOrderDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退单在线退款请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderOnlineRefundRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 退单信息
     */
    @Schema(description = "退单信息")
    @NotNull
    private ReturnOrderDTO returnOrder;

    /**
     * 退款单信息
     */
    @Schema(description = "退款单信息")
    @NotNull
    private RefundOrderDTO refundOrder;

    /**
     * 操作人信息
     */
    @Schema(description = "操作人信息")
    @NotNull
    private Operator operator;
}
