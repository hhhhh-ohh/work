package com.wanmi.sbc.vas.provider.impl.recommend.analysis;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.dw.api.request.RecommendEffectAnalysisRequest;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectChartDataResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectReportResponse;
import com.wanmi.sbc.dw.api.response.analysis.RecommendEffectStatisticsDataResponse;
import com.wanmi.sbc.vas.api.provider.recommend.analysis.RecommendPositionAnalysisProvider;
import com.wanmi.sbc.vas.api.request.recommend.analysis.RecommendPositionAnalysisReportRequest;
import com.wanmi.sbc.vas.recommend.analysis.RecommendPositionAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 推荐坑位效果分析接口实现
 *
 * @author lvzhenwei
 * @date 2021-04-07 10:55:53
 */
@RestController
@Validated
public class RecommendPositionAnalysisController implements RecommendPositionAnalysisProvider {

  @Autowired private RecommendPositionAnalysisService recommendPositionAnalysisService;

  @Override
  public BaseResponse<RecommendEffectStatisticsDataResponse> getRecommendEffectStatistics(
      @Valid RecommendEffectAnalysisRequest request) {
    return BaseResponse.success(
        recommendPositionAnalysisService.getRecommendEffectStatistics(request));
  }

  @Override
  public BaseResponse<RecommendEffectChartDataResponse> getRecommendEffectChartData(
      @Valid RecommendEffectAnalysisRequest request) {
    return BaseResponse.success(
        recommendPositionAnalysisService.getRecommendEffectChartData(request));
  }

  @Override
  public BaseResponse<RecommendEffectReportResponse> getRecommendEffectReportData(
      @Valid RecommendEffectAnalysisRequest request) {
    return BaseResponse.success(
            recommendPositionAnalysisService.getRecommendEffectReportData(request));
  }
}
