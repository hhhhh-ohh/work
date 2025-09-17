package com.wanmi.sbc.vas.api.response.recommend.recommendsystemconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendSystemConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>智能推荐配置列表结果</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendSystemConfigListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 智能推荐配置列表结果
     */
    @Schema(description = "智能推荐配置列表结果")
    private List<RecommendSystemConfigVO> recommendSystemConfigVOList;
}
