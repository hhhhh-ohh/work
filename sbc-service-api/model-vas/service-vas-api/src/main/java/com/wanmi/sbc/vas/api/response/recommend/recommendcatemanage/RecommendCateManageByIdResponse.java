package com.wanmi.sbc.vas.api.response.recommend.recommendcatemanage;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendCateManageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）分类推荐管理信息response</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateManageByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分类推荐管理信息
     */
    @Schema(description = "分类推荐管理信息")
    private RecommendCateManageVO recommendCateManageVO;
}
