package com.wanmi.ares.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.MarketingTypeBase;
import com.wanmi.ares.enums.StoreSelectType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 查询区间内营销活动最早开始时间
 */
@Data
@Schema
public class MarketingQueryEarliestDateRequest extends BaseRequest {

    // 营销类型
    @Schema(description = "营销类型", required = true)
    private MarketingType marketingType;

    // 店铺ID
    @Schema(description = "店铺ID")
    private Long storeId;

    // 营销ID
    @Schema(description = "营销ID")
    private List<String> marketingIds;

    // 拼团营销ID
    @Schema(description = "拼团营销ID")
    private List<String> grouponMarketingIds;

    //查询起始时间
    @Schema(description = "查询起始时间", hidden = true)
    private String queryDate;

    @Schema(description = "0全部，1商家，2门店")
    private StoreSelectType storeSelectType;

    /**
     * 营销类型集合
     */
    @Schema(description = "营销类型集合")
    private List<Integer> marketingTypeList;

    public List<Integer> getMarketingTypeList() {
        if (MarketingType.REDUCTION_DISCOUNT_GIFT.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.REDUCTION.toValue(),MarketingTypeBase.DISCOUNT.toValue(),MarketingTypeBase.GIFT.toValue());
        } else if (MarketingType.HALF_PRICE_SECOND_PIECE.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.HALF_PRICE_SECOND_PIECE.toValue());
        } else if (MarketingType.BUYOUT_PRICE.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.BUYOUT_PRICE.toValue());
        } else if (MarketingType.SUITS.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.SUITS.toValue());
        } else if (MarketingType.PREFERENTIAL.equals(marketingType)) {
            marketingTypeList = Arrays.asList(MarketingTypeBase.PREFERENTIAL.toValue());
        }
        return marketingTypeList;
    }

}
