package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.math.BigDecimal;

@Schema
@Data
public class DistributorCustomerInviteInfoUpdateRequest extends BaseRequest {

    @Schema(description = "分销员id")
    @NotBlank
    private String distributeId;

    @Schema(description = "发放邀新奖金人数")
    @Min(0)
    private Integer rewardCashCount = 0;

    @Schema(description = "未发放邀新奖金人数")
    @Min(0)
    private Integer rewardCashLimitCount = 0;

    @Schema(description = "未发放邀新奖金有效邀新人数")
    @Min(0)
    private Integer rewardCashAvailableLimitCount = 0;

    @Schema(description = "发放邀新奖励金额")
    @Min(0)
    private BigDecimal rewardCash;

    @Schema(description = "发放邀新优惠券人数")
    @Min(0)
    private Integer rewardCouponCount = 0;

    @Schema(description = "未发放邀新优惠券人数")
    @Min(0)
    private Integer rewardCouponLimitCount = 0;

    @Schema(description = "未发放邀新优惠券有效邀新人数")
    @Min(0)
    private Integer rewardCouponAvailableLimitCount = 0;

}
