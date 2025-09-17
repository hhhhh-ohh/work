package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
*
 * @description  营销算价返回
 * @author  wur
 * @date: 2022/2/24 10:37
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPriceItemMarketingVO extends BasicResponse {
    private static final long serialversionuid = 1L;

    /**
     * 营销Id
     */
    @Schema(description = "营销Id")
    private Long marketingId;

    /**
     * 营销等级Id
     */
    @Schema(description = "营销等级Id")
    private Long marketingLevelId;
    /**
     * 营销类型
     */
    private MarketingType marketingType;

    /**
     * 除去营销优惠金额后的商品均摊价
     */
    private BigDecimal splitPrice;

    /**
     * 优惠金额
     */
    private BigDecimal discountsAmount;

    /**
     * 活动赠品信息
     */
    private List<CountPriceItemGiftVO> giftList;

    /**
     * 活动赠券信息
     */
    private List<CountPriceItemReturnVO> returnList;

    /**
     * 加价购活动商品信息
     */
    private List<CountPriceItemForPreferentialVO> countPriceItemForPreferentialVOList;
}