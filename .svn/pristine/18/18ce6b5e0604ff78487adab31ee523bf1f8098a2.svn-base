package com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CateRelatedRecommendAddListRequest
 * @Description 分类相关性推荐新增List参数
 * @Author lvzhenwei
 * @Date 2020/11/26 16:48
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendAddListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 分类相关性推荐新增List
     */
    @Schema(description = "分类相关性推荐新增List")
    private List<CateRelatedRecommendAddRequest> cateRelatedRecommendAddRequestList;
}
