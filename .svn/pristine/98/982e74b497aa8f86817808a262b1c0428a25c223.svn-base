package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerAccountContractRequest
 * @description
 * @date 2022/7/20 7:23 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountContractRequest extends BaseRequest {
    private static final long serialVersionUID = -4110267936764356879L;

    /**
     * 业务id 商户id或customerId
     */
    @Schema(description = "业务id")
    @NotBlank
    private String businessId;
}
