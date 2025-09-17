package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.setting.bean.dto.PointsBasicRuleDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class PointsBasicRuleModifyRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 积分基础获取规则列表
     */
    @Schema(description = "积分基础获取规则列表")
    @NotNull
    @Valid
    private List<PointsBasicRuleDTO> pointsBasicRuleDTOList;
}
