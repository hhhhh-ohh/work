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
 * <p>智能推荐配置修改结果</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendSystemConfigModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的智能推荐配置信息
     */
    @Schema(description = "已修改的智能推荐配置信息")
    private RecommendSystemConfigVO recommendSystemConfigVO;
}
