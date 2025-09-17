package com.wanmi.sbc.marketing.api.request.market;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingLevelType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingGoodsForXsiteRequest extends BaseRequest {

    /**
     * 商品id列表
     */
    @NotEmpty
    @Schema(description = "商品id列表")
    private List<String> goodsInfoIds;

    /**
     * 生效的营销活动
     */
    @Schema(description = "生效的营销活动")
    private MarketingLevelType marketingLevelType;
}
