package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 分销员佣金，汇总统计
 */
@Schema
@Data
@NoArgsConstructor
public class DistributionCommissionStatisticsResponse extends BasicResponse {

    /**
     * 佣金总额
     */
    @Schema(description = "佣金总额")
    private BigDecimal commissionTotal;

    /**
     * 分销佣金总额
     */
    @Schema(description = "分销佣金总额")
    private BigDecimal commission;

    /**
     * 邀新奖金总额
     */
    @Schema(description = "邀新奖金总额")
    private BigDecimal rewardCash;

    /**
     * 未入账分销佣金总额
     */
    @Schema(description = "未入账分销佣金总额")
    private BigDecimal commissionNotRecorded;

    /**
     * 未入账邀新奖金总额
     */
    @Schema(description = "未入账邀新奖金总额")
    private BigDecimal rewardCashNotRecorded;

    public DistributionCommissionStatisticsResponse(BigDecimal commissionTotal,BigDecimal commission,
                                                    BigDecimal rewardCash,BigDecimal commissionNotRecorded,BigDecimal rewardCashNotRecorded){
        this.commissionTotal=commissionTotal;
        this.commission=commission;
        this.rewardCash=rewardCash;
        this.commissionNotRecorded=commissionNotRecorded;
        this.rewardCashNotRecorded=rewardCashNotRecorded;
    }
}
