package com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendPositionConfigurationVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>推荐坑位设置分页结果</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:04:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionConfigurationPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 推荐坑位设置分页结果
     */
    @Schema(description = "推荐坑位设置分页结果")
    private MicroServicePage<RecommendPositionConfigurationVO> recommendPositionConfigurationVOPage;
}
