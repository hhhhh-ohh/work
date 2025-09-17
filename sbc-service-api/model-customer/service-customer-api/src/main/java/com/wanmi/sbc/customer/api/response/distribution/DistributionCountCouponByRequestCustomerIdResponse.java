package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 邀新人数统计返回体
 * @Autho qiaokang
 * @Date：2019-03-07 19:10:58
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCountCouponByRequestCustomerIdResponse extends BasicResponse {

    /**
     * 已邀新总人数
     */
    @Schema(description = "已邀新总人数")
    private Long totalCount;

    /**
     * 有效邀新人数
     */
    @Schema(description = "有效邀新人数")
    private Long validCount;

    /**
     * 奖励已入账邀新人数
     */
    @Schema(description = "奖励已入账邀新人数")
    private Long recordedCount;
}
