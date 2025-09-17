package com.wanmi.sbc.marketing.api.request.marketingsuitssku;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsCountBySkuIdRequest extends BaseRequest {

    /**
     *  商品编号
     */
    @Schema(description ="商品编号")
    private String goodsInfoId;

    /**
     * 营销类型
     */
    @Schema(description = "营销类型")
    private MarketingType marketingType;

}
