package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerReceiverRelApplyRequest
 * @description
 * @date 2022/9/14 4:03 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelApplyRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = -7182435194057764367L;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    @NotNull
    private Long supplierId;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String customerId;
}
