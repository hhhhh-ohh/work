package com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration;

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
 * <p>推荐坑位设置列表结果</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:04:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionConfigurationListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 推荐坑位设置列表结果
     */
    @Schema(description = "推荐坑位设置列表结果")
    private List<RecommendPositionConfigurationVO> recommendPositionConfigurationVOList;
}
