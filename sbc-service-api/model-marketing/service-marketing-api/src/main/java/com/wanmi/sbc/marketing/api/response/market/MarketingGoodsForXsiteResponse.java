package com.wanmi.sbc.marketing.api.response.market;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class MarketingGoodsForXsiteResponse extends BasicResponse {

    /**
     * 商品id列表
     */
    @Schema(description = "商品id列表")
    private List<String> goodsInfoIds;
}
