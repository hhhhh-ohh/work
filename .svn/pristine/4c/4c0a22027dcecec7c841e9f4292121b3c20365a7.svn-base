package com.wanmi.sbc.vas.api.response.recommend.analysis;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.dw.bean.vo.RecommendEffectAnalysisResultVO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

/**
 * 智能推荐效果报表返回参数
 *
 * @author lvzhenwei
 * @date 2021/4/22 10:56 上午
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendEffectReportDataResponse extends BasicResponse {

    /** 推荐效果图表展示 */
    @Schema(description = "推荐效果报表Response对象")
    private MicroServicePage<RecommendEffectAnalysisResultVO> recommendEffectAnalysisReportData;
}
