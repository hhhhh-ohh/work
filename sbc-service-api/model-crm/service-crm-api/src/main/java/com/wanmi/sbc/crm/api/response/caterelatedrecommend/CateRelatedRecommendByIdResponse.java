package com.wanmi.sbc.crm.api.response.caterelatedrecommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CateRelatedRecommendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）分类相关性推荐信息response</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分类相关性推荐信息
     */
    @Schema(description = "分类相关性推荐信息")
    private CateRelatedRecommendVO cateRelatedRecommendVO;
}
