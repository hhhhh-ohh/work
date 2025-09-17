package com.wanmi.sbc.customer.api.response.points;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>权益优惠券关联表response</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPointsStatisticsResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 历史发放积分统计
     */
    private Long pointsIssueStatictics;

    /**
     * 可用积分统计
     */
    private Long pointsAvailableStatictics;

}