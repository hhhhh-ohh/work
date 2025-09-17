package com.wanmi.sbc.order.optimization.trade1.confirm;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.request.trade.FindTradeSnapshotRequest;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liguang
 * @description 订单快照过程 中间参数类
 * @date 2022/3/3 17:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeConfirmParam implements Serializable {
    private static final long serialVersionUID = -5726406372674338097L;

    @Schema(description = "购物车/拼团提交页请求信息")
    private TradeBuyRequest tradeBuyRequest;

    @Schema(description = "查询订单快照请求")
    private FindTradeSnapshotRequest findTradeSnapshotRequest;

    @Schema(description = "查询订单快照返回")
    private TradeConfirmResponse tradeConfirmResponse;

    @Schema(description = "会员信息")
    private CustomerVO customerVO;

    @Schema(description = "店铺ids")
    @Builder.Default
    private List<Long> storeIds = Lists.newArrayList();

    @Schema(description = "店铺信息")
    @Builder.Default
    private List<StoreVO> stores = Lists.newArrayList();

    /**
     * <storeId, openFlag>
     */
    @Schema(description = "店铺是否开启分销开关")
    @Builder.Default
    private Map<Long, DefaultFlag> storeOpenFlag = Maps.newHashMap();

    @Schema(description = "是否是社交分销")
    @Builder.Default
    private Boolean isDistributFlag = Boolean.FALSE;

    @Schema(description = "系统积分开关")
    private Boolean systemPointFlag;

    @Schema(description = "是否是企业会员")
    private Boolean isIepCustomerFlag;

    @Schema(description = "购买商品skuIds")
    private List<String> skuIds = Lists.newArrayList();

    @Schema(description = "商品SPU信息")
    @Builder.Default
    private List<GoodsVO> goodses = Lists.newArrayList();

    @Schema(description = "商品SKU信息")
    @Builder.Default
    private List<GoodsInfoVO> goodsInfos = Lists.newArrayList();

    @Schema(description = "供应商商品SKU信息")
    @Builder.Default
    private List<GoodsInfoVO> providerGoodsInfos = Lists.newArrayList();

    @Schema(description = "营销信息")
    @Builder.Default
    private Map<String, List<MarketingPluginLabelDetailVO>> marketingMap = Maps.newHashMap();

    @Schema(description = "购物车选中的商品的营销")
    @Builder.Default
    private Map<String, Long> goodsMarketingMap = Maps.newHashMap();

    @Schema(description = "赠品SPU信息")
    @Builder.Default
    private List<GoodsVO> giftGoodses = Lists.newArrayList();

    @Schema(description = "赠品SKU信息")
    @Builder.Default
    private List<GoodsInfoVO> giftGoodsInfos = Lists.newArrayList();

    @Schema(description = "加价购商品SPU信息")
    @Builder.Default
    private List<GoodsVO> preferentialGoodses = Lists.newArrayList();

    @Schema(description = "加价购商品SKU信息")
    @Builder.Default
    private List<GoodsInfoVO> preferentialGoodsInfos = Lists.newArrayList();

    @Schema(description = "预售商品信息")
    @Builder.Default
    private List<BookingSaleVO> bookingSaleVOS = Lists.newArrayList();

    @Schema(description = "区间价列表")
    @Builder.Default
    private List<GoodsIntervalPriceVO> goodsIntervalPriceVOList = Lists.newArrayList();

    @Schema(description = "商品规格列表")
    @Builder.Default
    private List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelVOList = Lists.newArrayList();

    @Schema(description = "赠品商品规格列表")
    @Builder.Default
    private List<GoodsInfoSpecDetailRelVO> giftGoodsInfoSpecDetailRelVOList = Lists.newArrayList();

    @Schema(description = "加价购商品规格列表")
    @Builder.Default
    private List<GoodsInfoSpecDetailRelVO> preferentialGoodsInfoSpecDetailRelVOList = Lists.newArrayList();

    @Schema(description = "sku的redis库存 包含供应商商品")
    @Builder.Default
    private Map<String, Long> skuRedisStockMap = Maps.newHashMap();

    @Schema(description = "linkedMall开关")
    @Builder.Default
    private Boolean linkedMallFlag = Boolean.FALSE;

    @Schema(description = "vop开关是否打开")
    @Builder.Default
    private Boolean vopFlag = Boolean.FALSE;

    @Schema(description = "订单快照map")
    @Builder.Default
    private Map<String, TradeItem> tradeItemMap = Maps.newHashMap();

    @Schema(description = "算价后明细")
    @Builder.Default
    private List<CountPriceItemVO> countPriceVOList = Lists.newArrayList();

    @Schema(description = "订单快照")
    private TradeItemSnapshot tradeItemSnapshot;

    @Schema(description = "用户提货卡信息")
    private UserGiftCardInfoVO userGiftCardInfoVO;

    @Schema(description = "社区团购活动表信息")
    private CommunityActivityVO communityActivityVO;

    @Schema(description = "社区团购团长信息")
    private CommunityLeaderVO communityLeaderVO;

    @Schema(description = "团长自提点表列表结果")
    @Builder.Default
    private List<CommunityLeaderPickupPointVO> communityLeaderPickupPointList = Lists.newArrayList();

    @Schema(description = "社区团购商品-活动价")
    @Builder.Default
    private Map<String, BigDecimal> skuIdToActivityPriceMap = new HashMap<>();
}