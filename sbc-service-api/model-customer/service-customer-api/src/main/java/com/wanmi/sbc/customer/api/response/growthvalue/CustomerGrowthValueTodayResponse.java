package com.wanmi.sbc.customer.api.response.growthvalue;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGrowthValueTodayResponse extends BasicResponse {

    /**
     * 今日到账成长值总数
     */
    @Schema(description = "今日到账成长值总数")
    private Integer growthValueSum;
}
