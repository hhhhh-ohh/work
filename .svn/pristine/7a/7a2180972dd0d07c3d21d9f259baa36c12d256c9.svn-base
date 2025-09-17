package com.wanmi.sbc.crm.api.response.recommendsystemconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.RecommendSystemConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）智能推荐配置信息response</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendSystemConfigByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 智能推荐配置信息
     */
    @Schema(description = "智能推荐配置信息")
    private RecommendSystemConfigVO recommendSystemConfigVO;
}
