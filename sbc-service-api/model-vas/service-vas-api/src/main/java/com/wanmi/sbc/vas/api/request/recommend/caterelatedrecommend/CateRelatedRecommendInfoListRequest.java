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
 * @ClassName CateRelatedRecommendInfoListRequest
 * @Description 商品相关性推荐
 * @Author lvzhenwei
 * @Date 2020/11/26 14:25
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendInfoListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 类目id
     */
    @Schema(description = "类目id")
    private Long cateId;

    /**
     * 类目idList
     */
    @Schema(description = "类目idList")
    List<Long> cateIds;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortColumn;

    /**
     * 排序规则 desc asc
     */
    @Schema(description = "排序规则 desc asc")
    private String sortRole;
}
