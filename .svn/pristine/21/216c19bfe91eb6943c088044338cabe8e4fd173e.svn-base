package com.wanmi.sbc.empower.api.request.pay;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.math.BigDecimal;

/**
 * <p>退款请求参数</p>
 * Created by of628-wenzhi on 2017-08-04-下午6:02.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundRequest extends PayBaseRequest {

    private static final long serialVersionUID = -3092485338458308665L;
    /**
     * 退单业务id
     */
    @Schema(description = "退单业务id")
    @NotNull
    private String refundBusinessId;

    /**
     * 关联的订单业务id
     */
    @Schema(description = "关联的订单业务id")
    @NotNull
    private String businessId;

    /**
     * 如果是合并支付。businessId是父订单号。非合并支付businessId和tid值一样
     * 当前退款的订单ID
     */
    @Schema(description = "关联的订单业务id")
    private String tid;

    /**
     * 退款金额，单位：元
     */
    @Schema(description = "退款金额，单位：元")
    @NotNull
    private BigDecimal amount;

    /**
     * 订单金额，单位：元
     */
    @Schema(description = "订单金额，单位：元")
    private BigDecimal totalPrice;

    /**
     * 发起退款请求的客户端ip
     */
    @Schema(description = "发起退款请求的客户端ip")
    @NotBlank
    private String clientIp;

    /**
     * 退款描述
     */
    @Schema(description = "退款描述")
    private String description;

    /**
     * 支付对象id，数据处理使用，无需传入
     */
    @Schema(description = "支付对象id，数据处理使用，无需传入")
    private String payObjectId;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "商户id-boss端取默认值")
    @NotNull
    private Long storeId;

    @Schema(description = "订单的支付单号")
    private String payNo;
}
