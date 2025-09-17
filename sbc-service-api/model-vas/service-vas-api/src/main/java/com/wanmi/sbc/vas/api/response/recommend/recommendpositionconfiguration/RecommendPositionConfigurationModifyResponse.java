package com.wanmi.sbc.vas.api.response.recommend.recommendpositionconfiguration;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendPositionConfigurationVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>推荐坑位设置修改结果</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:04:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionConfigurationModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的推荐坑位设置信息
     */
    @Schema(description = "已修改的推荐坑位设置信息")
    private RecommendPositionConfigurationVO recommendPositionConfigurationVO;
}
