package com.wanmi.sbc.vas.api.response.recommend.IntelligentRecommendation;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendPositionConfigurationVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>商品智能推荐查询结果</p>
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentRecommendationResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品智能推荐查询结果
     */
    @Schema(description = "商品智能推荐查询结果")
    private List<String> goodsIdList;

    /**
     * 商品智能推荐查询结果
     */
    @Schema(description = "商品智能推荐查询结果")
    private List<Long> cateIdList;

    /**
     * 返回的兴趣品牌List集合
     */
    private List<Long> interestBrandList;

    @Schema(description = "坑位设置")
    private RecommendPositionConfigurationVO recommendPositionConfigurationVO;
}
