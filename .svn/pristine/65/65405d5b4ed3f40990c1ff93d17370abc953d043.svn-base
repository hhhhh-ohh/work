package com.wanmi.sbc.crm.api.request.recommendsystemconfig;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p></p>
 *
 * @author: lvzhenwei
 * @time: 2020/11/28 14:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendSystemConfigRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 键
     */
    @Schema(description = "键")
    @NotBlank
    private String configKey;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private String configType;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String configName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 状态,0:未启用1:已启用
     */
    @Schema(description = "状态,0:未启用1:已启用")
    private Integer status;

    /**
     * 配置内容，如JSON内容
     */
    @Schema(description = "配置内容，如JSON内容")
    private String context;

    /**
     * 删除标识,0:未删除1:已删除
     */
    @Schema(description = "删除标识,0:未删除1:已删除", hidden = true)
    private DeleteFlag delFlag;
}
