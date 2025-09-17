package com.wanmi.sbc.customer.api.request.distribution.performance;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>查询分销员昨日业绩数据request</p>
 * Created by of628-wenzhi on 2019-06-18-16:48.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class DistributionPerformanceYesterdayQueryRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 分销员id
     */
    @NotBlank
    @Schema(description = "分销员id")
    private String distributionId;
}
