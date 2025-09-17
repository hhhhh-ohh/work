package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Schema
@Data
public class DistributorCustomerInviteInfoReviseRequest extends BaseRequest {

    @Schema(description = "邀请人会员id")
    @NotBlank
    private String customerId;

    /**
     * 分销员标识UUID
     */
    @Schema(description = "分销员标识UUID")
    @NotBlank
    private String distributionId;

}
