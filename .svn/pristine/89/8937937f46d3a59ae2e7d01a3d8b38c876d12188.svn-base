package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.setting.bean.dto.GrowthValueBasicRuleDTO;
import com.wanmi.sbc.setting.bean.enums.GrowthValueRule;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class GrowthValueBasicRuleModifyRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 成长值基础获取规则列表
     */
    @Schema(description = "成长值基础获取规则列表")
    @NotNull
    @Valid
    private List<GrowthValueBasicRuleDTO> growthValueBasicRuleDTOList;

    /**
     * 成长值规则
     */
    @Schema(description = "成长值规则")
    @NotNull
    private GrowthValueRule rule;
}
