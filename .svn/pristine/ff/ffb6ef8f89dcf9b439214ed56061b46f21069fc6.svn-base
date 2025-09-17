package com.wanmi.sbc.vas.api.provider.recommend.analysis;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.dw.api.request.RecommendEffectAnalysisRequest;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectChartDataResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectReportResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectStatisticsDataResponse;
import com.wanmi.sbc.vas.api.request.recommend.analysis.RecommendPositionAnalysisReportRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>推荐坑位效果分析Provider</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@FeignClient(value = "${application.vas.name}", contextId = "RecommendPositionAnalysisProvider")
public interface RecommendPositionAnalysisProvider {

    /**
     * 获取智能推荐坑位分析数据
     * @param request
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/recommend-position-analysis/get-recommend-effect-statistics")
    BaseResponse<RecommendEffectStatisticsDataResponse> getRecommendEffectStatistics(@RequestBody @Valid RecommendEffectAnalysisRequest request);

    /**
     *查询推荐效果分析趋势
     *
     * @param request
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/recommend-position-analysis/get-recommend-effect-chart-data")
    BaseResponse<RecommendEffectChartDataResponse> getRecommendEffectChartData(@RequestBody @Valid RecommendEffectAnalysisRequest request);

    /**
     * 获取推荐坑位分析报表
     * @param request
     * @return
     */
    @PostMapping("/vas/${application.vas.version}/recommend-position-analysis/get-recommend-effect-report-data")
    BaseResponse<RecommendEffectReportResponse> getRecommendEffectReportData(@RequestBody @Valid RecommendEffectAnalysisRequest request);

}
