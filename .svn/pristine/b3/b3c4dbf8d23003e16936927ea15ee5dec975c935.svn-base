package com.wanmi.ares.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.ares.enums.MarketingType;
import com.wanmi.ares.enums.MarketingTypeBase;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SelectMarketingRequest extends BaseRequest {
    /**
     * 页码
     */
    @Schema(description = "页码")
    private int pageNum = 0;

    /**
     * 页面大小
     */
    @Schema(description = "页面大小")
    private int pageSize = 10;

    // 营销类型
    @Schema(description = "营销类型", required = true)
    private MarketingType marketingType;

    // 活动名称
    @Schema(description = "活动名称")
    private String marketingName;

    // 店铺名称
    @Schema(description = "店铺名称")
    private String storeName;

    // 店铺id
    @Schema(description = "店铺id")
    private Long storeId;

     @Schema(hidden=true)
    private String queryDate;

    /**
     * 搜索条件:秒杀活动时间
     * 如果选择秒杀活动时，选择了活动日期，查询时间则为所选日期
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime fullTimeBegin;

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
