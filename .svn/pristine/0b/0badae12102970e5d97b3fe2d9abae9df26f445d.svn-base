package com.wanmi.sbc.customer.api.request.distribution;


import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>分销员邀新信息新增参数</p>
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class DistributionCustomerInviteInfoAddRequest extends CustomerBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 分销员标识UUID
     */
    @Schema(description = "分销员标识UUID")
    private String distributionId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 已发放邀新奖励现金人数
     */
    @Schema(description = "已发放邀新奖励现金人数")
    private Integer rewardCashCount;

    /**
     * 达到上限未发放奖励现金人数-从
     */
    @Schema(description = "达到上限未发放奖励现金人数")
    private Integer rewardCashLimitCount;


    /**
     * 达到上限未发放奖励现金有效邀新人数-从
     */
    @Schema(description = "达到上限未发放奖励现金有效邀新人数")
    private Integer rewardCashAvailableLimitCount;

    /**
     * 已发放邀新奖励优惠券人数-至
     */
    @Schema(description = "已发放邀新奖励优惠券人数")
    private Integer rewardCouponCount;

    /**
     * 达到上限未发放奖励优惠券人数-从
     */
    @Schema(description = "达到上限未发放奖励优惠券金人数")
    private Integer rewardCouponLimitCount;

    /**
     * 达到上限未发放奖励优惠券有效邀新人数-从
     */
    @Schema(description = "达到上限未发放奖励优惠券金有效邀新人数")
    private Integer rewardCouponAvailableLimitCount;

}