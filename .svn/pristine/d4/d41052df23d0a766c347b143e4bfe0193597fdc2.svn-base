package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.math.BigDecimal;

@Schema
@Data
public class AfterSupplyAgainUpdateDistributorRequest extends BaseRequest {

    @Schema(description = "分销员id")
    @NotBlank
    private String distributeId;

    @Schema(description = "发放邀新奖励金额")
    @Min(0)
    private BigDecimal rewardCash;

    @Schema(description = "未发放的邀新奖励金额")
    @Min(0)
    private BigDecimal rewardCashNotRecorded;
}
