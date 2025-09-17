package com.wanmi.sbc.marketing.api.request.discount;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.MarketingFullReductionLevelDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-21
 */
@Schema
@Data
public class MarketingFullReductionSaveLevelListRequest extends BaseRequest {

    private static final long serialVersionUID = 5636931747065029394L;

    @Schema(description = "营销满减多级优惠列表")
    @NotNull
    @Size(min = 1)
    private List<MarketingFullReductionLevelDTO> fullReductionLevelList;
}
