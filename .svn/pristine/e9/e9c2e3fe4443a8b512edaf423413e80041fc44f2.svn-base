package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.order.bean.vo.PurchaseGoodsViewVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@Data
@Schema
public class PurchaseMarketingCalcResponse extends BasicResponse {
    /**
     * 采购单参与营销活动的商品列表
     */
    @Schema(description = "采购单参与营销活动的商品列表")
    private List<GoodsInfoVO> goodsInfoList;

    /**
     * 采购单参与营销活动的商品总数
     */
    @Schema(description = "采购单参与营销活动的商品总数")
    private Long totalCount;

    /**
     * 采购单参与营销活动的商品总金额
     */
    @Schema(description = "采购单参与营销活动的商品总金额")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    /**
     * 采购单达到营销级别的差额，满金额是缺少的金额，满数量是缺少的数量
     */
    @Schema(description = "采购单达到营销级别的差额,满金额是缺少的金额，满数量是缺少的数量")
    private BigDecimal lack;

    /**
     * 优惠力度，满折是折扣，满减是减多少
     */
    @Schema(description = "优惠力度,满折是折扣，满减是减多少")
    private BigDecimal discount;

    /**
     * 一口价
     */
    @Schema(description = "一口价")
    private BigDecimal buyoutPrice;

    /**
     * 商家Id  0：boss,  other:其他商家
     */
    @Schema(description = "商家Id")
    private Long storeId;

    /**
     * 营销id
     */
    @Schema(description = "营销id")
    private Long marketingId;

    /**
     * 促销类型 0：满减 1:满折 2:满赠
     */
    @Schema(description = "促销类型")
    private MarketingType marketingType;

    /**
     * 促销子类型 0：满金额减 1：满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠
     */
    @Schema(description = "促销子类型")
    private MarketingSubType subType;

    /**
     * 满减等级，没有满足等级的则为最小等级，满足等级的则为满足等级里的最大一个
     */
    @Schema(description = "满减等级")
    private MarketingFullReductionLevelVO fullReductionLevel;

    /**
     * 满折等级，没有满足等级的则为最小等级，满足等级的则为满足等级里的最大一个
     */
    @Schema(description = "满折等级")
    private MarketingFullDiscountLevelVO fullDiscountLevel;

    /**
     * 满赠等级，没有满足等级的则为最小等级，满足等级的则为满足等级里的最大一个
     */
    @Schema(description = "满赠等级")
    private MarketingFullGiftLevelVO fullGiftLevel;

    /**
     * 满赠营销下所有等级列表
     */
    @Schema(description = "满赠营销下所有等级列表")
    private List<MarketingFullGiftLevelVO> fullGiftLevelList;

    /**
     * 打包一口价等级，没有满足等级的则为最小等级，满足等级的则为满足等级里的最大一个
     */
    @Schema(description = "打包一口价等级")
    private MarketingBuyoutPriceLevelVO buyoutPriceLevel;

    /**
     * 第二件半价等级，折扣=0时，是送一件，折扣！=0就按照第几件计算第几折
     */
    @Schema(description = "第二件半价等级")
    private MarketingHalfPriceSecondPieceLevelVO halfPriceSecondPieceLevel;

    /**
     * 赠品SKU信息
     */
    @Schema(description = "赠品SKU信息")
    private PurchaseGoodsViewVO giftGoodsInfoResponse;

    /**
     * 满返等级，没有满足等级的则为最小等级，满足等级的则为满足等级里的最大一个
     */
    @Schema(description = "满返等级")
    private MarketingFullReturnLevelVO fullReturnLevel;

    /**
     * 满返营销下所有等级列表
     */
    @Schema(description = "满返营销下所有等级列表")
    private List<MarketingFullReturnLevelVO> fullReturnLevelList;

    /**
     * 加价购等级，没有满足等级的则为最小等级，满足等级的则为满足等级里的最大一个
     */
    @Schema(description = "加价购等级")
    private MarketingPreferentialLevelVO preferentialLevel;

    /**
     * 加价购营销下所有等级列表
     */
    @Schema(description = "加价购营销下所有等级列表")
    private List<MarketingPreferentialLevelVO> preferentialLevelList;

    /**
     * 加价购商品SKU信息
     */
    @Schema(description = "加价购商品SKU信息")
    private PurchaseGoodsViewVO preferentialGoodsInfoResponse;
}
