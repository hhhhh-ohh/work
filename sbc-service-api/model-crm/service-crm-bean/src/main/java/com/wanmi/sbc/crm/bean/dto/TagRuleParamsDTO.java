package com.wanmi.sbc.crm.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * 二级维度-参数信息
 */
@Schema
@Data
public class TagRuleParamsDTO {

    @Schema(description = "标签参数id")
    @NotNull
    private Long paramId;

    @Schema(description = "字段名称", hidden = true)
    private String columnMame;

    @Schema(description = "维度配置类型", hidden = true)
    private Integer type;

    @Schema(description = "标签维度类型", hidden = true)
    private Integer tagDimensionType;

    @Schema(description = "标签参数值")
    @NotEmpty
    private List<String> values;

    @Schema(description = "范围选项列表")
    private List<String> ranges;
}
