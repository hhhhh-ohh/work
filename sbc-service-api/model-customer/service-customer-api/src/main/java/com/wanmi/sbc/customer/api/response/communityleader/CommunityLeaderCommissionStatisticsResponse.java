package com.wanmi.sbc.customer.api.response.communityleader;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 社区团长佣金佣金，汇总统计
 */
@Schema
@Data
@NoArgsConstructor
public class CommunityLeaderCommissionStatisticsResponse extends BasicResponse {

    /**
     * 已入账佣金
     */
    @Schema(description = "已入账佣金")
    private BigDecimal commissionReceived = BigDecimal.ZERO;

    /**
     * 已入账自提佣金
     */
    @Schema(description = "已入账自提佣金")
    private BigDecimal commissionReceivedPickup = BigDecimal.ZERO;

    /**
     * 已入账帮卖佣金
     */
    @Schema(description = "已入账帮卖佣金")
    private BigDecimal commissionReceivedAssist = BigDecimal.ZERO;

    /**
     * 未入账佣金
     */
    @Schema(description = "未入账佣金")
    private BigDecimal commissionPending = BigDecimal.ZERO;

    /**
     * 未入账自提佣金
     */
    @Schema(description = "未入账自提佣金")
    private BigDecimal commissionPendingPickup = BigDecimal.ZERO;

    /**
     * 未入账帮卖佣金
     */
    @Schema(description = "未入账帮卖佣金")
    private BigDecimal commissionPendingAssist = BigDecimal.ZERO;
}
