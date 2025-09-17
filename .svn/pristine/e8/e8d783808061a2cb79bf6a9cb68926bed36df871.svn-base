package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerReceiverBatchRequest
 * @description
 * @date 2022/7/14 2:42 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverBatchRequest extends BaseRequest {
    private static final long serialVersionUID = -7524478096527313586L;

    @NotBlank
    @Schema(description = "清分账户id")
    private String accountId;
}
