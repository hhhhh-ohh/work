package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className DistributionLedgerRelRequest
 * @description
 * @date 2022/7/20 1:52 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionLedgerRelRequest extends BaseRequest {
    private static final long serialVersionUID = -5859936333518237564L;

    @Schema(description = "会员id")
    @NotBlank
    private String customerId;
}
