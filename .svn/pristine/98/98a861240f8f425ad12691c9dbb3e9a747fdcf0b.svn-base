package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
public class CountPriceItemVO extends BasicResponse {
    private static final long serialversionuid = 1L;

    private Long storeId;

    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private List<CountPriceItemGoodsInfoVO> goodsInfoList;

    /**
     * 营销活动信息
     */
    @Schema(description = "营销活动信息")
    private List<TradeMarketingVO> tradeMarketings = new ArrayList<>();

    /**
     * 活动赠品信息
     */
    @Schema(description = "活动赠品信息")
    private List<CountPriceItemGiftVO> giftList = new ArrayList<>();

    /**
     * 加价购活动商品信息
     */
    @Schema(description = "加价购活动商品信息")
    private List<CountPriceItemForPreferentialVO> preferentialGoodsList = new ArrayList<>();

    /**
     * 满返优惠券信息
     */
    @Schema(description = "满返优惠券信息")
    private List<CountPriceItemReturnVO> returnList = new ArrayList<>();

}