package com.wanmi.sbc.vas.recommend.analysis;

import com.wanmi.sbc.dw.api.provider.analysis.RecommendEffectAnalysisProvider;
import com.wanmi.sbc.dw.api.request.RecommendEffectAnalysisRequest;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectChartDataResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectReportResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectStatisticsDataResponse;
import com.wanmi.sbc.vas.api.request.recommend.analysis.RecommendPositionAnalysisReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendPositionAnalysisService {

    @Autowired private RecommendEffectAnalysisProvider recommendEffectAnalysisProvider;

    /**
     * 获取智能推荐坑位分析数据
     *
     * @param request
     */
    public RecommendEffectStatisticsDataResponse getRecommendEffectStatistics(
            RecommendEffectAnalysisRequest request) {
        return recommendEffectAnalysisProvider.getRecommendEffectStatistics(request).getContext();
    }

    /**
     * 查询推荐效果分析趋势
     *
     * @param request
     * @return
     */
    public RecommendEffectChartDataResponse getRecommendEffectChartData(
            RecommendEffectAnalysisRequest request) {
        return recommendEffectAnalysisProvider.getRecommendEffectChartData(request).getContext();
    }

    /**
     * 获取推荐坑位分析报表
     *
     * @author lvzhenwei
     * @date 2021/4/17 10:35 上午
     * @param request
     * @return com.wanmi.sbc.dw.api.response.analysis.RecommendEffectReportResponse
     */
    public RecommendEffectReportResponse getRecommendEffectReportData(
            RecommendEffectAnalysisRequest request) {
        return recommendEffectAnalysisProvider.getRecommendEffectReportData(request).getContext();
    }
}
