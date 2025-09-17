package com.wanmi.ares.request.marketing;

import com.wanmi.ares.enums.MarketingType;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.MarketingTypeBase;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author 10486
 * @Date 11:18 2021/1/11
 **/
@Data
public class MarketingAnalysisJobRequest extends BaseRequest {

    private String initDate;

    private String marketingType;

    private List<Integer> marketingTypeList;

    public List<Integer> getMarketingTypeList() {
        if (StringUtils.isNotBlank(marketingType) && marketingType.equals(
                "reduction_discount_gift")){
            marketingTypeList = Arrays.asList(MarketingTypeBase.REDUCTION.toValue(),MarketingTypeBase.DISCOUNT.toValue(),MarketingTypeBase.GIFT.toValue());
        } else if(StringUtils.isNotBlank(marketingType) && marketingType.equals(
                "suits")){
            marketingTypeList = Arrays.asList(MarketingTypeBase.SUITS.toValue());
        } else if (StringUtils.isNotBlank(marketingType) && marketingType.equals("half_price_second_piece")){
            marketingTypeList = Arrays.asList(MarketingTypeBase.HALF_PRICE_SECOND_PIECE.toValue());
        } else if ( StringUtils.isNotBlank(marketingType) && marketingType.equals("buyout_price")){
            marketingTypeList = Arrays.asList(MarketingTypeBase.BUYOUT_PRICE.toValue());
        } else if (StringUtils.isNotBlank(marketingType) && marketingType.equals("preferential")) {
            marketingTypeList = Arrays.asList(MarketingTypeBase.PREFERENTIAL.toValue());
        }
        return marketingTypeList;
    }
}
