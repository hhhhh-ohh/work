package com.wanmi.sbc.marketing.api.response.distributionrecord;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>根据分该分销员的业绩</p>
 * @author baijz
 * @date 2019-03-08 10:28:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class DistributionRecordByInviteeIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 昨日销售额
     */
    @Schema(description = "昨日销售额")
    private BigDecimal yesterdaySales;

    /**
     * 昨日预估收益
     */
    @Schema(description = "昨日预估收益")
    private BigDecimal yesterdayEstimatedReturn;

    /**
     * 本月销售额
     */
    @Schema(description = "本月销售额")
    private BigDecimal monthSales;

    /**
     * 本月预估收益
     */
    @Schema(description = "本月预估收益")
    private BigDecimal monthEstimatedReturn;
}
