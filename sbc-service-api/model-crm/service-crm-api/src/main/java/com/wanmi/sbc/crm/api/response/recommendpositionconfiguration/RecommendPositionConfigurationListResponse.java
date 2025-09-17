package com.wanmi.sbc.crm.api.response.recommendpositionconfiguration;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.RecommendPositionConfigurationVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

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
