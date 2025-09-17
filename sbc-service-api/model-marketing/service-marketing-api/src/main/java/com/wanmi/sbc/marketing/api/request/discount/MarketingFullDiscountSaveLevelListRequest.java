package com.wanmi.sbc.marketing.api.request.discount;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.MarketingFullDiscountLevelDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-20
 */
@Schema
@Data
public class MarketingFullDiscountSaveLevelListRequest extends BaseRequest {

    private static final long serialVersionUID = -2329732931582062039L;

    @Schema(description = "营销满折多级优惠列表")
    @NotNull
    @Size(min = 1)
    private List<MarketingFullDiscountLevelDTO> discountLevelList;

}
