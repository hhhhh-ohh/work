package com.wanmi.sbc.marketing.api.response.buyoutprice;

import com.wanmi.sbc.marketing.bean.vo.MarketingBuyoutPriceLevelVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * Date: 2020-04-14
 * @author weiwenhao
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingBuyoutPriceMarketingIdResponse extends MarketingVO implements Serializable{

    private static final long serialVersionUID = 2290492944510194828L;

    @Schema(description = "一口价营销活动规则列表")
    private List<MarketingBuyoutPriceLevelVO> marketingBuyoutPriceLevelVO;


}
