package com.wanmi.sbc.crm.bean.dto;

import com.wanmi.sbc.crm.bean.enums.RelationType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 一级维度-规则信息
 */
@Schema
@Data
public class TagRuleDTO {

    @Schema(description = "标签维度id")
    @NotNull
    private Long dimensionId;

    @Schema(description = "二级维度且或关系，0：且，1：或")
    @NotNull
    private RelationType type;

    @Schema(description = "表名", hidden = true)
    private String tableName;

    @Schema(description = "二级维度-聚合目标类参数")
    private TagRuleResultParamsDTO resultParam;

    @Schema(description = "二级维度-条件类参数列表")
    @NotEmpty
    private List<TagRuleParamsDTO> ruleParamList;
}
