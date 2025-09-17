package com.wanmi.sbc.empower.api.request.pay.ali;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: service-pay
 * @description: 支付宝退款接口请求参数
 * @create: 2019-02-15 11:10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AliPayRefundRequest {

    /**
     * 关联的订单业务id
     */
    @Schema(description = "关联的订单业务id")
    @NotNull
    private String businessId;

    @Schema(description = "退单业务id")
    private String refundBusinessId;

    /**
     * 退款金额，单位：元
     */
    @Schema(description = "退款金额，单位：元")
    @NotNull
    private BigDecimal amount;

    /**
     * 退款描述
     */
    @Schema(description = "退款描述")
    private String description;

    @Schema(description = "支付宝APPID")
    private String appid;

    @Schema(description = "支付宝商户私钥")
    private String appPrivateKey;

    @Schema(description = "支付宝公钥")
    private String aliPayPublicKey;

}
