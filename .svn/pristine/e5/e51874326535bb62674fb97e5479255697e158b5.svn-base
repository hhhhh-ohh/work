package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.enums.RelationType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 一级维度-规则信息
 */
@Schema
@Data
public class TagRuleVO extends BasicResponse {

    @Schema(description = "标签维度id")
    private Long dimensionId;

    @Schema(description = "二级维度且或关系，0：且，1：或")
    private RelationType type;

    @Schema(description = "表名", hidden = true)
    private String tableName;

    @Schema(description = "二级维度-聚合目标类参数")
    private TagRuleResultParamsVO resultParam;

    @Schema(description = "二级维度-参数信息列表")
    private List<TagRuleParamsVO> ruleParamList;
}
