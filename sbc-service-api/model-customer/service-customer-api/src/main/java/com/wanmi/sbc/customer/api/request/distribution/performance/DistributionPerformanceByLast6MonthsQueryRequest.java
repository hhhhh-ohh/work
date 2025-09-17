package com.wanmi.sbc.customer.api.request.distribution.performance;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>分销业绩按月查询请求参数</p>
 * Created by of628-wenzhi on 2019-04-18-18:39.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class DistributionPerformanceByLast6MonthsQueryRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 分销员id
     */
    @NotBlank
    @Schema(description = "分销员id")
    private String distributionId;
}
