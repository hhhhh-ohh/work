package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.RefundBillDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 平台退单线下退款请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderOfflineRefundForBossRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 退单id
     */
    @Schema(description = "退单id")
    @NotBlank
    private String rid;

    /**
     * 退款流水
     */
    @Schema(description = "退款流水")
    @NotNull
    private RefundBillDTO refundBill;

    /**
     * 操作人信息
     */
    @Schema(description = "操作人信息")
    @NotNull
    private Operator operator;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @NotBlank
    private String tid;
}
