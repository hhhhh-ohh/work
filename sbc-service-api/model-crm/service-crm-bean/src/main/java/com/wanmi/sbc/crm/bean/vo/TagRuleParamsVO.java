package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 二级维度-参数信息
 */
@Schema
@Data
public class TagRuleParamsVO extends BasicResponse {

    @Schema(description = "标签参数id")
    private Long paramId;

    @Schema(description = "字段名称")
    private String columnMame;

    @Schema(description = "维度配置类型")
    private Integer type;

    @Schema(description = "标签维度类型")
    private Integer tagDimensionType;

    @Schema(description = "标签参数值")
    private List<String> values;

    @Schema(description = "范围选项列表")
    private List<String> ranges;

    @Schema(description = "标签参数值的名称")
    private List<String> valueNames;
}
