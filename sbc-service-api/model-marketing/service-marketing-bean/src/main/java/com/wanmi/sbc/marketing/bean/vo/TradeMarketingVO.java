package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeMarketingVO extends BasicResponse {

    private static final long serialVersionUID = 8995541597631480709L;
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
     * 营销名称
     */
    @Schema(description = "营销名称")
    private String marketingName;

    /**
     * 营销类型 0：满减 1:满折 2:满赠
     */
    @Schema(description = "营销活动类型")
    private MarketingType marketingType;

    /**
     * 该营销活动关联的订单商品id集合
     */
    @Schema(description = "该营销活动关联的订单商品id集合")
    private List<String> skuIds;

    /**
     * 营销子类型 0：满金额减 1：满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠
     */
    @Schema(description = "营销子类型")
    private MarketingSubType subType;

    /**
     * 满折信息
     */
    @Schema(description = "营销满折多级优惠信息")
    private MarketingFullDiscountLevelVO fullDiscountLevel;

    /**
     * 满赠信息
     */
    @Schema(description = "营销满赠多级优惠信息")
    private MarketingFullGiftLevelVO giftLevel;

    /**
     * 满减信息
     */
    @Schema(description = "营销满减多级优惠信息")
    private MarketingFullReductionLevelVO reductionLevel;

    /**
     * 打包一口价信息
     */
    @Schema(description = "营销打包一口价多级优惠信息")
    private MarketingBuyoutPriceLevelVO buyoutPriceLevel;

    /**
     * 第二件半价信息
     */
    @Schema(description = "营销第二件半价优惠信息")
    private MarketingHalfPriceSecondPieceLevelVO halfPriceSecondPieceLevel;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountsAmount;

    /**
     * 该活动关联商品除去优惠金额外的应付金额
     */
    @Schema(description = "该活动关联商品除去优惠金额外的应付金额")
    private BigDecimal realPayAmount;

    /**
     * 当前满赠活动关联的赠品id列表，非满赠活动则为空
     */
    @Schema(description = "当前满赠活动关联的赠品id列表，非满赠活动则为空")
    private List<String> giftIds = new ArrayList<>();

    /**
     * 满返信息
     */
    @Schema(description = "营销满返多级优惠信息")
    private MarketingFullReturnLevelVO returnLevel;

    /**
     * 当前满返活动关联的赠券id列表，非满返活动则为空
     */
    @Schema(description = "当前满返活动关联的赠券id列表，非满返活动则为空")
    private List<String> returnIds = new ArrayList<>();

    @Schema(description = "营销加价购多级优惠信息")
    private List<MarketingPreferentialLevelVO> preferentialLevelVOS;

    @Schema(description = "当前加价购活动关联的赠品id列表，非加价购活动则为空")
    private List<String> preferentialIds = new ArrayList<>();
}
