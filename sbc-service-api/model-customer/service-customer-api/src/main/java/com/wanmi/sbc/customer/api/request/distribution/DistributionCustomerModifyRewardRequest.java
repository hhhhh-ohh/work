package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 分销员奖励更新请求
 * @Autho qiaokang
 * @Date：2019-03-14 14:17:31
 */
@Schema
@Data
public class DistributionCustomerModifyRewardRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 分销员标识UUID
     */
    @Schema(description = "分销员标识UUID")
    @NotBlank
    private String distributionId;

    /**
     * 邀新人数
     */
    @Schema(description = "邀新人数")
    private Integer inviteCount = 0;

    /**
     * 邀新奖金(元)
     */
    @Schema(description = "邀新奖金(元)")
    private BigDecimal rewardCash =  BigDecimal.ZERO;

    /**
     * 未入账邀新奖金(元)
     */
    @Schema(description = "未入账邀新奖金(元)")
    private BigDecimal rewardCashNotRecorded =  BigDecimal.ZERO;

    /**
     * 分销订单(笔)
     */
    @Schema(description = "分销订单(笔)")
    private Integer distributionTradeCount = 0 ;

    /**
     * 销售额(元)
     */
    @Schema(description = "销售额(元)")
    private BigDecimal sales =  BigDecimal.ZERO;

    /**
     * 未入账分销佣金(元)
     */
    @Schema(description = "未入账分销佣金(元)")
    private BigDecimal commissionNotRecorded =  BigDecimal.ZERO;

    /**
     * 佣金总额
     */
    @Schema(description = "邀新奖金(元)")
    private BigDecimal commissionTotal =  BigDecimal.ZERO;

}
