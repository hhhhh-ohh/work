package com.wanmi.sbc.vas.api.request.recommend.analysis;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName RecommendPositionAnalysisQueryRequest
 * @Description 推荐坑位效果分析报表统计request
 * @Author lvzhenwei
 * @Date 2021/04/02 16:48
 **/
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionAnalysisReportRequest extends RecommendPositionAnalysisQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报表纬度,0：天报表，1：商品报表，2：类目报表，3：品牌报表
     */
    @Schema(description = "报表纬度,0：天报表，1：商品报表，2：类目报表，3：品牌报表")
    private Integer reportType;

    /**
     * 类目等级，0：一级类目，1：二类目，2：三级类目
     */
    @Schema(description = "类目等级，0：一级类目，1：二类目，2：三级类目")
    private Integer cateType;

    /**
     * 查询条件关键字
     */
    @Schema(description = "查询条件关键字")
    private String selectName;

}
