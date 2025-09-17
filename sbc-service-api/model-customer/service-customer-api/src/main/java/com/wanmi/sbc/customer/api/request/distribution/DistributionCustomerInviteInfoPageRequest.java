package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>分销员邀新信息分页查询请求参数</p>
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerInviteInfoPageRequest extends BaseQueryRequest {


    private static final long serialVersionUID = 1L;
    /**
     * 批量查询-分销员标识UUIDList
     */
    @Schema(description = "批量查询-分销员标识")
    private List<String> distributionIdList;

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
     * 已发放邀新奖励现金人数-至
     */
    @Schema(description = "已发放邀新奖励现金人数-至")
    private Integer rewardCashCountEnd;

    /**
     * 达到上限未发放奖励现金人数-从
     */
    @Schema(description = "达到上限未发放奖励现金人数-从")
    private Integer rewardCashLimitCountStart;

    /**
     * 达到上限未发放奖励现金有效邀新人数-从
     */
    @Schema(description = "达到上限未发放奖励现金有效邀新人数-从")
    private Integer rewardCashAvailableLimitCountStart;

    /**
     * 已发放邀新奖励优惠券人数-至
     */
    @Schema(description = "已发放邀新奖励优惠券人数-至")
    private Integer rewardCouponCountEnd;

    /**
     * 达到上限未发放奖励优惠券人数-从
     */
    @Schema(description = "达到上限未发放奖励优惠券金人数-从")
    private Integer rewardCouponLimitCountStart;

    /**
     * 达到上限未发放奖励优惠券有效邀新人数-从
     */
    @Schema(description = "达到上限未发放奖励优惠券金有效邀新人数-从")
    private Integer rewardCouponAvailableLimitCountStart;

}