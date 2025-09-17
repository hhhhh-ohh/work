package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoResponseVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingStoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 15:57
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingForEndVO extends MarketingVO {

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
     * 满返
     */
    @Schema(description = "满返")
    private List<MarketingFullReturnLevelVO> fullReturnLevelList;

    /**
     * 营销关联商品
     */
    @Schema(description = "营销关联商品信息")
    private GoodsInfoResponseVO goodsList;

    /**
     * 参与门店数量
     */
    @Schema(description = "参与门店数量")
    private Long storeNum;

    /**
     * 参与门店列表
     */
    @Schema(description = "参与门店列表")
    private List<StoreVO> participateStoreList;

    /**
     * 满返店铺信息
     */
    private List<MarketingFullReturnStoreVO> marketingFullReturnStoreList;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型")
    private MarketingStoreType storeType;

    /**
     * 满返参与店铺数量
     */
    @Schema(description = "满返参与店铺数量")
    private Long returnStoreNum;

    /**
     * 满返参与店铺列表
     */
    @Schema(description = "满返参与店铺列表")
    private List<StoreVO> returnStoreInfoList;

    /**
     * 赠券列表
     */
    private List<CouponInfoVO> returnList;

    /**
     * 加价购等级
     */
    private List<MarketingPreferentialLevelVO> preferentialLevelList;

    @Override
    public List<Long> getJoinLevelList() {
        return Arrays.stream(getJoinLevel().split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

}
