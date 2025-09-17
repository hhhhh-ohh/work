package com.wanmi.sbc.marketing.common.model.entity;

import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>订单营销信息</p>
 * Created by of628-wenzhi on 2018-02-26-下午5:57.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeMarketing {
    /**
     * 促销Id
     */
    private Long marketingId;

    /**
     * 促销名称
     */
    private String marketingName;

    /**
     * 促销类型 0：满减 1:满折 2:满赠
     */
    private MarketingType marketingType;

    /**
     * 该营销活动关联的订单商品id集合
     */
    private List<String> skuIds;

    /**
     * 促销子类型 0：满金额减 1：满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠
     */
    private MarketingSubType subType;

    /**
     * 满折信息
     */
    private MarketingFullDiscountLevelVO fullDiscountLevel;

    /**
     * 满赠信息
     */
    private MarketingFullGiftLevelVO giftLevel;

    /**
     * 满减信息
     */
    private MarketingFullReductionLevelVO reductionLevel;

    /**
     * 打包一口价信息
     */
    private MarketingBuyoutPriceLevelVO buyoutPriceLevel;

    /**
     * 第二件半价价信息
     */
    private MarketingHalfPriceSecondPieceLevelVO halfPriceSecondPieceLevel;

    /**
     * 优惠金额
     */
    private BigDecimal discountsAmount;

    /**
     * 该活动关联商品除去优惠金额外的应付金额
     */
    private BigDecimal realPayAmount;

    /**
     * 当前满赠活动关联的赠品id列表，非满赠活动则为空
     */
    private List<String> giftIds = new ArrayList<>();

    /**
     * 满返信息
     */
    private MarketingFullReturnLevelVO fullReturnLevel;

    /**
     * 当前加价购活动关联的赠品id列表，非加价购活动则为空
     */
    private List<MarketingPreferentialLevelVO> preferentialLevelVOS;

    /**
     * 营销加价购多级优惠信息
     */
    private List<String> preferentialIds = new ArrayList<>();
}
