package com.wanmi.sbc.account.api.request.ledgerfunds;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerFundsByCustomerIdRequest
 * @description
 * @date 2022/7/25 7:32 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFundsByCustomerIdRequest extends BaseRequest {
    private static final long serialVersionUID = -791215057987714328L;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    @NotBlank
    private String customerId;
}
