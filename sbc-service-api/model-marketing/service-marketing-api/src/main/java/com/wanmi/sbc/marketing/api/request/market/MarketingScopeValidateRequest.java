package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.goods.bean.dto.GoodsMutexValidateDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: 戴倚天
 * @Description: 营销验证入参
 * @Date: 2022-04-29 9:37
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingScopeValidateRequest extends GoodsMutexValidateDTO {

    @Schema(description = "满系营销类型")
    private List<MarketingType> marketingTypes;

    @Schema(description = "范围")
    private List<MarketingScopeType> scopeTypes;

    @Schema(description = "非当前id")
    private Long notId;
}
