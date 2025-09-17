package com.wanmi.ares.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.MarketingTypeBase;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.sbc.common.enums.MarketingAllType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Schema
public class MarketingQueryRequest extends BaseRequest {

    /**
     * 营销类型
     */
    @Schema(description = "营销类型", required = true)
    private MarketingType marketingType;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 营销ID
     */
    @Schema(description = "营销ID")
    private List<String> marketingIds;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;

    /**
     * 统计周期类型 7：天；8：周
     */
    @Schema(description = "统计周期类型 7：天；8：周")
    private StatisticsDataType type;

     @Schema(hidden=true)
    private String queryDate;

    @Schema(description = "统计周期", hidden = true)
    private int period;

    /**
     * 拼团营销ID集合
     */
    @Schema(description = "拼团营销ID集合")
    private List<String> grouponMarketingIds;


    /**
     * 营销类型集合
     */
    @Schema(description = "营销类型集合")
    private List<Integer> marketingTypeList;

    @Schema(description = "0全部，1商家，2门店")
    private StoreSelectType storeSelectType;

    public List<Integer> getMarketingTypeList() {
        if (MarketingType.REDUCTION_DISCOUNT_GIFT.equals(marketingType)){
            marketingTypeList = Arrays.asList(MarketingTypeBase.REDUCTION.toValue(),MarketingTypeBase.DISCOUNT.toValue(),MarketingTypeBase.GIFT.toValue());
        }  else if (MarketingType.HALF_PRICE_SECOND_PIECE.equals(marketingType)){
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
