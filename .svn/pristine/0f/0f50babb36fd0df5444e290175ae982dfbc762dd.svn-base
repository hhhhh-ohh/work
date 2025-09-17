package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.marketing.bean.enums.MarketingStoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


/**
 * @author zhanggaolei
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingSimpleVO extends MarketingVO {

    private static final long serialVersionUID = 909316613702026711L;
    /**
     * 满减等级
     */
    @Schema(description = "营销满减多级优惠列表")
    private List<MarketingFullReductionLevelVO> fullReductionLevelList;

    /**
     * 满折等级
     */
    @Schema(description = "营销满折多级优惠列表")
    private List<MarketingFullDiscountLevelVO> fullDiscountLevelList;

    /**
     * 满赠等级
     */
    @Schema(description = "营销满赠多级优惠列表")
    private List<MarketingFullGiftLevelVO> fullGiftLevelList;

    /**
     * 打包一口价等级
     */
    @Schema(description = "营销打包一口价多级优惠列表")
    private List<MarketingBuyoutPriceLevelVO> buyoutPriceLevelList;

    /**
     * 第二件半价
     */
    @Schema(description = "第二件半价")
    private List<MarketingHalfPriceSecondPieceLevelVO> halfPriceSecondPieceLevel;
    /**
     * 满返店铺
     */
    @Schema(description = "满返店铺列表")
    private List<MarketingFullReturnStoreVO> marketingFullReturnStoreList;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型")
    private MarketingStoreType storeType;

}
