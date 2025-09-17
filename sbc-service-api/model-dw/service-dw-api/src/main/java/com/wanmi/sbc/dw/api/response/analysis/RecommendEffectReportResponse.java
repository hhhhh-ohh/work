package com.wanmi.sbc.dw.api.response.analysis;

import com.wanmi.sbc.dw.bean.vo.RecommendEffectAnalysisResultVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: com.wanmi.sbc.dw.api.response.analysis
 * @Description: 离线报表数据统计
 * @Author: 何军红
 * @Time: 9:56 9:56
 */
@Data
@Schema
public class RecommendEffectReportResponse {
    /**
     * 推荐效果图表展示
     */
    @Schema(description = "推荐效果报表Response对象")
    private List<RecommendEffectAnalysisResultVO> recommendEffectAnalysisReportData;

    @Schema(description = "总条数")
    private Integer totalNum;
}
