package com.wanmi.sbc.vas.api.response.recommend.caterelatedrecommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.CateRelatedRecommendVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>分类相关性推荐新增结果</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的分类相关性推荐信息
     */
    @Schema(description = "已新增的分类相关性推荐信息")
    private CateRelatedRecommendVO cateRelatedRecommendVO;
}
