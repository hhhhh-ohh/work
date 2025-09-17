package com.wanmi.sbc.dw.api.provider.analysis;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.dw.api.request.RecommendEffectAnalysisRequest;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectChartDataResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectReportResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectStatisticsDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @ClassName: com.wanmi.sbc.dw.api.provider.analysis
 * @Description: 推荐结果分析 api
 * @Author: 何军红
 * @Time: 14:38
 */
@FeignClient(value = "${application.dw.name}", contextId = "RecommendEffectAnalysisProvider")
public interface RecommendEffectAnalysisProvider {
    /**
     * 指标统计
     *
     * @param request
     * @return
     */
    @PostMapping("/dw/${application.dw.version}/analysis/value-statistics")
    BaseResponse<RecommendEffectStatisticsDataResponse> getRecommendEffectStatistics(@RequestBody @Valid RecommendEffectAnalysisRequest request);

    /**
     * 图表展示
     *
     * @param request
     * @return
     */
    @PostMapping("/dw/${application.dw.version}/analysis/chart-statistics")
    BaseResponse<RecommendEffectChartDataResponse> getRecommendEffectChartData(@RequestBody @Valid RecommendEffectAnalysisRequest request);


    /**
     * 报表
     *
     * @param request
     * @return
     */
    @PostMapping("/dw/${application.dw.version}/analysis/report-statistics")
    BaseResponse<RecommendEffectReportResponse> getRecommendEffectReportData(@RequestBody @Valid RecommendEffectAnalysisRequest request);
}
