package com.wanmi.sbc.marketing.api.request.communitystatistics;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author edz
 * @className CommunityStatisticsUpdateRequest
 * @description 数据统计
 * @date 2023/8/4 15:35
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityStatisticsUpdateRequest implements Serializable {
    @Schema(description = "支付总额")
    private BigDecimal payTotal;
    @Schema(description = "帮卖总额")
    private BigDecimal assistOrderTotal;
    @Schema(description = "未入账佣金")
    private BigDecimal commissionPending;
    @Schema(description = "未入账自提佣金")
    private BigDecimal commissionPendingPickup;
    @Schema(description = "未入账帮卖佣金")
    private BigDecimal commissionPendingAssist;
    @Schema(description = "活动ID")
    private String activityId;
    @Schema(description = "团长ID")
    private String leaderId;
}
