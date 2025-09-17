package com.wanmi.sbc.crm.api.response.caterelatedrecommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CateRelatedRecommendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分类相关性推荐列表结果</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分类相关性推荐列表结果
     */
    @Schema(description = "分类相关性推荐列表结果")
    private List<CateRelatedRecommendVO> cateRelatedRecommendVOList;
}
