package com.wanmi.sbc.order.optimization.trade1.purchase.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DistributionCommissionUtils;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerListForOrderCommitRequest;
import com.wanmi.sbc.customer.bean.enums.CommissionPriorityType;
import com.wanmi.sbc.customer.bean.enums.CommissionUnhookType;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerSimVO;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionCacheQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeListForUseByCustomerIdRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardTradeRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityFreeDeliveryByIdRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsValidRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodeListForUseByCustomerIdResponse;
import com.wanmi.sbc.marketing.api.response.distribution.MultistageSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardTradeResponse;
import com.wanmi.sbc.marketing.api.response.marketingsuits.MarketingSuitsValidResponse;
import com.wanmi.sbc.marketing.bean.dto.TradeItemInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.CouponCodeStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.order.api.request.trade.FindTradeSnapshotRequest;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.groupon.service.GrouponOrderService;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.trade.model.entity.GrouponTradeValid;
import com.wanmi.sbc.order.trade.model.entity.TradeGrouponCommitForm;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.service.TradeServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author liguang
 * @description note something here
 * @date 2022/4/7 20:10
 */
@Service
@Slf4j
public class TradePurchaseDealService {

    @Autowired
    private GrouponOrderService grouponOrderService;
    @Autowired
    private TradeServiceInterface tradeServiceInterface;
    @Autowired
    private GrouponActivityQueryProvider grouponActivityQueryProvider;
    @Autowired
    private MarketingSuitsQueryProvider marketingSuitsQueryProvider;
    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;
    @Autowired
    private DistributionCacheQueryProvider distributionCacheQueryProvider;
    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    /**
     * 处理佣金
     * @param param
     */
    public void dealTotalCommission(TradeConfirmParam param){
        BigDecimal totalCommission = BigDecimal.ZERO;
        TradeConfirmResponse tradeConfirmResponse = param.getTradeConfirmResponse();
        List<TradeConfirmItemVO> tradeConfirmItems = tradeConfirmResponse.getTradeConfirmItems();
        if(CollectionUtils.isEmpty(tradeConfirmItems)){
            return;
        }
        DistributeChannel channel = param.getFindTradeSnapshotRequest().getDistributeChannel();
        //积分商品抵扣开关是否打开
        for (TradeConfirmItemVO tradeItemGroupVO : tradeConfirmItems) {
            if(DefaultFlag.YES.equals(param.getTradeConfirmResponse().getStoreBagsFlag())){
                continue;
            }
            List<TradeItemVO> distributionTradeItems = Lists.newArrayList();
            List<TradeItemVO> tradeItems = tradeItemGroupVO.getTradeItems();
            for (TradeItemVO tradeItem : tradeItems) {
                DefaultFlag storeOpenFlag = param.getStoreOpenFlag().get(tradeItem.getStoreId());
                // 积分价商品不可以参与分销
                boolean pointFlag = param.getSystemPointFlag() && Objects.nonNull(tradeItem.getBuyPoint()) && tradeItem.getBuyPoint() > 0L;
                if (DistributionGoodsAudit.CHECKED == tradeItem.getDistributionGoodsAudit()
                        && DefaultFlag.YES.equals(storeOpenFlag)
                        && param.getIsDistributFlag()
                        && !pointFlag) {
                    tradeItem.setSplitPrice(tradeItem.getOriginalPrice().multiply(new BigDecimal(tradeItem.getNum())));
                    tradeItem.setPrice(tradeItem.getOriginalPrice());
                    tradeItem.setLevelPrice(tradeItem.getOriginalPrice());
                    // 初步计算分销佣金
                    tradeItem.setDistributionCommission(tradeItem.getSplitPrice().multiply(tradeItem.getCommissionRate()));
                } else {
                    tradeItem.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                }
                if(DistributionGoodsAudit.CHECKED.equals(tradeItem.getDistributionGoodsAudit())){
                    distributionTradeItems.add(tradeItem);
                }
            }

            if(CollectionUtils.isEmpty(distributionTradeItems)){
                continue;
            }
            // 2.设置分销相关字段
            MultistageSettingGetResponse multistageSetting = this.distributionCacheQueryProvider.getMultistageSetting().getContext();

            // 2.1.查询佣金受益人列表
            DistributionCustomerListForOrderCommitRequest request = new DistributionCustomerListForOrderCommitRequest();
            request.setBuyerId(param.getCustomerVO().getCustomerId());
            request.setCommissionPriorityType(CommissionPriorityType.fromValue(multistageSetting.getCommissionPriorityType().toValue()));
            request.setCommissionUnhookType(CommissionUnhookType.fromValue(multistageSetting.getCommissionUnhookType().toValue()));
            request.setDistributorLevels(multistageSetting.getDistributorLevels());
            request.setInviteeId(channel.getInviteeId());
            request.setIsDistributor(tradeConfirmResponse.getIsDistributor());
            List<DistributionCustomerSimVO> inviteeCustomers = this.distributionCustomerQueryProvider.listDistributorsForOrderCommit(request).getContext().getDistributorList();
            if(CollectionUtils.isEmpty(inviteeCustomers)){
                continue;
            }
            // 2.2.根据受益人列表设置分销相关字段
            Map<String, DistributorLevelVO> distributorLevelMap = multistageSetting.getDistributorLevels().stream().collect(Collectors.toMap(DistributorLevelVO::getDistributorLevelId, Function.identity(), (v1, v2) -> v1));
            DistributionCustomerSimVO customer = inviteeCustomers.get(0);
            DistributorLevelVO level = Optional.ofNullable(distributorLevelMap.get(customer.getDistributorLevelId())).orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000003));
            // 2.2.1.设置返利人信息
            if (customer.getCustomerId().equals(param.getCustomerVO().getCustomerId())) {
                totalCommission = distributionTradeItems.stream()
                        .map(item -> DistributionCommissionUtils.calDistributionCommission(item.getDistributionCommission(), level.getCommissionRate()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .add(totalCommission);
            }
        }
        tradeConfirmResponse.setTotalCommission(totalCommission);
    }

    /**
     * 校验拼团订单
     * @param param
     */
    public void validGrouponOrder(TradeConfirmParam param){
        TradeItemGroup tradeItemGroup = param.getTradeItemSnapshot().getItemGroups().get(NumberUtils.INTEGER_ZERO);
        TradeGrouponCommitForm grouponForm = tradeItemGroup.getGrouponForm();
        if(isNull(grouponForm)){
            return;
        }

        TradeItem tradeItem = tradeItemGroup.getTradeItems().get(NumberUtils.INTEGER_ZERO);
        if(!DistributionGoodsAudit.COMMON_GOODS.equals(tradeItem.getDistributionGoodsAudit())){
            log.error("拼团单，不能下分销商品");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 1.校验拼团商品
        GrouponGoodsInfoVO grouponGoodsInfo = this.grouponOrderService.validGrouponImmediately(
                GrouponTradeValid.builder()
                        .buyCount(tradeItem.getNum().intValue()).customerId(param.getCustomerVO().getCustomerId()).goodsId(tradeItem.getSpuId())
                        .goodsInfoId(tradeItem.getSkuId())
                        .grouponNo(grouponForm.getGrouponNo())
                        .openGroupon(grouponForm.getOpenGroupon())
                        .build());
        if (isNull(grouponGoodsInfo)) {
            log.error("拼团单下的不是拼团商品");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        TradeConfirmResponse tradeConfirmResponse = param.getTradeConfirmResponse();
        TradeConfirmItemVO tradeConfirmItemVO = tradeConfirmResponse.getTradeConfirmItems().get(NumberUtils.INTEGER_ZERO);
        TradeItemVO tradeItemVO = tradeConfirmItemVO.getTradeItems().get(NumberUtils.INTEGER_ZERO);
        // 2.设置拼团活动信息
        boolean freeDelivery = this.grouponActivityQueryProvider.getFreeDeliveryById(
                new GrouponActivityFreeDeliveryByIdRequest(grouponGoodsInfo.getGrouponActivityId())).getContext().isFreeDelivery();
        tradeConfirmResponse.setOpenGroupon(grouponForm.getOpenGroupon());
        tradeConfirmResponse.setGrouponFreeDelivery(freeDelivery);

        // 3.设成拼团价
        BigDecimal grouponPrice = grouponGoodsInfo.getGrouponPrice();
        BigDecimal splitPrice = grouponPrice.multiply(new BigDecimal(tradeItem.getNum()));
        tradeItemVO.setSplitPrice(splitPrice);
        tradeItemVO.setPrice(grouponPrice);
        tradeItemVO.setLevelPrice(grouponPrice);
        tradeItemVO.setBuyPoint(NumberUtils.LONG_ZERO);
        tradeConfirmItemVO.getTradePrice().setGoodsPrice(splitPrice);
        tradeConfirmItemVO.getTradePrice().setTotalPrice(splitPrice);
        tradeConfirmItemVO.getTradePrice().setBuyPoints(NumberUtils.LONG_ZERO);
        tradeConfirmResponse.setTotalBuyPoint(NumberUtils.LONG_ZERO);
    }

    /**
     * 校验组合购活动
     * @param param
     */
    public void validSuitOrder(TradeConfirmParam param){
        TradeItemGroup tradeItemGroup = param.getTradeItemSnapshot().getItemGroups().get(0);
        if(!Objects.equals(tradeItemGroup.getSuitMarketingFlag(), Boolean.TRUE) || CollectionUtils.isEmpty(tradeItemGroup.getTradeMarketingList())){
            return;
        }
        TradeConfirmResponse tradeConfirmResponse = param.getTradeConfirmResponse();
        TradeConfirmItemVO confirmItem = tradeConfirmResponse.getTradeConfirmItems().get(NumberUtils.INTEGER_ZERO);
        List<TradeItemVO> tradeItems = confirmItem.getTradeItems();
        // 获取并校验组合购活动信息
        MarketingSuitsValidRequest marketingSuitsValidRequest = new MarketingSuitsValidRequest();
        marketingSuitsValidRequest.setMarketingId(tradeItemGroup.getTradeMarketingList().get(NumberUtils.INTEGER_ZERO).getMarketingId());
        marketingSuitsValidRequest.setBaseStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        marketingSuitsValidRequest.setUserId(param.getCustomerVO().getCustomerId());
        Map<String, MarketingSuitsSkuVO> marketingSuitsSkuMap = Optional.ofNullable(marketingSuitsQueryProvider.validSuitOrderBeforeCommit(marketingSuitsValidRequest))
                .map(BaseResponse::getContext)
                .map(MarketingSuitsValidResponse::getMarketingSuitsSkuVOList)
                .orElseGet(Lists::newArrayList)
                .stream()
                .collect(Collectors.toMap(MarketingSuitsSkuVO::getSkuId, Function.identity(), (v1, v2) -> v1));
        for (TradeItemVO tradeItemVO : tradeItems) {
            MarketingSuitsSkuVO suitsSku = marketingSuitsSkuMap.get(tradeItemVO.getSkuId());
            if(nonNull(suitsSku)){
                // 设置组合购商品价格
                BigDecimal discountPrice = suitsSku.getDiscountPrice();
                BigDecimal splitPrice = discountPrice.multiply(new BigDecimal(suitsSku.getNum()));
                tradeItemVO.setSplitPrice(splitPrice);
                tradeItemVO.setPrice(discountPrice);
                tradeItemVO.setLevelPrice(discountPrice);
                tradeItemVO.setBuyPoint(NumberUtils.LONG_ZERO);
            }
        }
        BigDecimal goodsPrice = confirmItem.getTradeItems().stream().map(TradeItemVO::getSplitPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        confirmItem.getTradePrice().setGoodsPrice(goodsPrice);
        confirmItem.getTradePrice().setTotalPrice(goodsPrice);
        List<TradeConfirmMarketingVO> tradeConfirmMarketingVOS = new ArrayList<>();
        tradeItemGroup
                .getTradeMarketingList()
                .forEach(
                        tradeMarketingDTO -> {
                            TradeConfirmMarketingVO tradeConfirmMarketingVO =
                                    new TradeConfirmMarketingVO();
                            tradeConfirmMarketingVO.setMarketingId(
                                    tradeMarketingDTO.getMarketingId());
                            tradeConfirmMarketingVO.setMarketingLevelId(
                                    tradeMarketingDTO.getMarketingLevelId());
                            tradeConfirmMarketingVO.setMarketingType(MarketingType.SUITS.toValue());
                            tradeConfirmMarketingVOS.add(tradeConfirmMarketingVO);
                        });
        confirmItem.setTradeConfirmMarketingList(tradeConfirmMarketingVOS);
        tradeConfirmResponse.setTotalBuyPoint(NumberUtils.LONG_ZERO);
    }

    /**
     * 处理满系营销
     */
    public void dealMarketingInfoNew(TradeConfirmItemVO tradeConfirmItem, List<TradeMarketingDTO> tradeMarketingList, CountPriceItemVO countPriceItemVO){
        tradeConfirmItem.setTradeConfirmMarketingList(Lists.newArrayList());
        tradeConfirmItem.setDiscountsPrice(Lists.newArrayList());
        if(CollectionUtils.isEmpty(tradeMarketingList) || isNull(countPriceItemVO)){
            return;
        }

        TradePriceVO tradePriceVO = tradeConfirmItem.getTradePrice();
        DecimalFormat fmt = new DecimalFormat("#.##");
        Map<Long, TradeMarketingVO> tradeMarketingVOMap = countPriceItemVO.getTradeMarketings().stream().collect(Collectors.toMap(TradeMarketingVO::getMarketingId, Function.identity(), (v1, v2) -> v1));
        for (TradeMarketingDTO tradeMarketing : tradeMarketingList) {
            TradeMarketingVO tradeMarketingVO = tradeMarketingVOMap.get(tradeMarketing.getMarketingId());
            Long levelId = tradeMarketing.getMarketingLevelId();
            if (nonNull(tradeMarketingVO)) {
                TradeConfirmMarketingVO confirmMarketingVO = new TradeConfirmMarketingVO();
                confirmMarketingVO.setMarketingId(tradeMarketingVO.getMarketingId());
                confirmMarketingVO.setSkuIds(tradeMarketing.getSkuIds());
                confirmMarketingVO.setMarketingLevelId(levelId);
                confirmMarketingVO.setMarketingType(tradeMarketingVO.getMarketingType().toValue());
                List<TradeItemVO> items = tradeConfirmItem.getTradeItems().stream().filter(t -> tradeMarketing.getSkuIds().contains(t.getSkuId())).collect(Collectors.toList());
                String desc = "该营销不存在";
                if (MarketingType.REDUCTION.equals(tradeMarketingVO.getMarketingType())) {
                    tradeConfirmItem.getDiscountsPrice().add(DiscountsVO.builder().amount(tradeMarketingVO.getDiscountsAmount()).type(tradeMarketingVO.getMarketingType()).build());
                    this.clacSplitPrice(items, tradeMarketingVO.getRealPayAmount());
                    MarketingFullReductionLevelVO level = tradeMarketingVO.getReductionLevel();
                    if (nonNull(level)) {
                        if (MarketingSubType.REDUCTION_FULL_AMOUNT.equals(tradeMarketingVO.getSubType())) {
                            desc = String.format("您已满足满%s元减%s元", fmt.format(level.getFullAmount()), fmt.format(level.getReduction()));
                        } else {
                            desc = String.format("您已满足满%s件减%s元", level.getFullCount(), fmt.format(level.getReduction()));
                        }
                    }
                } else if (MarketingType.DISCOUNT.equals(tradeMarketingVO.getMarketingType())) {
                    tradeConfirmItem.getDiscountsPrice().add(DiscountsVO.builder().amount(tradeMarketingVO.getDiscountsAmount()).type(tradeMarketingVO.getMarketingType()).build());
                    this.clacSplitPrice(items, tradeMarketingVO.getRealPayAmount());
                    MarketingFullDiscountLevelVO level = tradeMarketingVO.getFullDiscountLevel();
                    if (nonNull(level)) {
                        if (MarketingSubType.DISCOUNT_FULL_AMOUNT.equals(tradeMarketingVO.getSubType())) {
                            desc = String.format("您已满足满%s元享%s折", fmt.format(level.getFullAmount()), fmt.format(level.getDiscount().multiply(new BigDecimal(10))));
                        } else {
                            desc = String.format("您已满足满%s件享%s折", level.getFullCount(), fmt.format(level.getDiscount().multiply(new BigDecimal(10))));
                        }
                    }
                } else if (MarketingType.GIFT.equals(tradeMarketingVO.getMarketingType())) {
                    MarketingFullGiftLevelVO level = tradeMarketingVO.getGiftLevel();
                    if (nonNull(level)) {
                        if (MarketingSubType.GIFT_FULL_AMOUNT.equals(tradeMarketingVO.getSubType())) {
                            desc = String.format("您已满足满%s元获赠品", fmt.format(level.getFullAmount()));
                        } else {
                            desc = String.format("您已满足满%s件获赠品", level.getFullCount());
                        }
                        Map<String, Long> skuNumMap = level.getFullGiftDetailList().stream().collect(Collectors.toMap(MarketingFullGiftDetailVO::getProductId, MarketingFullGiftDetailVO::getProductNum));
                        confirmMarketingVO.setGiftSkuIds(Lists.newArrayList(skuNumMap.keySet()));
                        List<TradeItemVO> tradeItems = tradeConfirmItem.getGifts()
                                .stream()
                                .filter(t -> tradeMarketing.getGiftSkuIds().contains(t.getSkuId())
                                        && Objects.equals(tradeMarketing.getMarketingId(), t.getMarketingIds().get(0)))
                                .collect(Collectors.toList());
                        for (TradeItemVO tradeItem : tradeItems) {
                            Long produceNum = skuNumMap.get(tradeItem.getSkuId());
                            if(tradeItem.getNum() >= produceNum){
                                tradeItem.setNum(produceNum);
                            }else {
                                tradeItem.setGoodsStatus(GoodsStatus.OUT_STOCK);
                            }
//                            tradeItem.getMarketingIds().add(level.getMarketingId());
                            tradeItem.getMarketingLevelIds().add(level.getGiftLevelId());
                        }
                    }
                } else if (MarketingType.BUYOUT_PRICE.equals(tradeMarketingVO.getMarketingType())) {
                    tradeConfirmItem.getDiscountsPrice().add(DiscountsVO.builder().amount(tradeMarketingVO.getDiscountsAmount()).type(tradeMarketingVO.getMarketingType()).build());
                    this.clacSplitPrice(items, tradeMarketingVO.getRealPayAmount());
                    MarketingBuyoutPriceLevelVO level = tradeMarketingVO.getBuyoutPriceLevel();
                    if (nonNull(level)) {
                        desc = String.format("您已满足%s件%s元", level.getChoiceCount(), fmt.format(level.getFullAmount()));
                    }
                } else if (MarketingType.HALF_PRICE_SECOND_PIECE.equals(tradeMarketingVO.getMarketingType())) {
                    tradeConfirmItem.getDiscountsPrice().add(DiscountsVO.builder().amount(tradeMarketingVO.getDiscountsAmount()).type(tradeMarketingVO.getMarketingType()).build());
                    this.clacSplitPrice(items, tradeMarketingVO.getRealPayAmount());
                    MarketingHalfPriceSecondPieceLevelVO level = tradeMarketingVO.getHalfPriceSecondPieceLevel();
                    if (nonNull(level)) {
                        if (BigDecimal.ZERO.compareTo(level.getDiscount()) == 0) {
                            desc = String.format("您已满足买%s送1", level.getNumber() - 1);
                        } else {
                            desc = String.format("您已满足第%s件%s折", level.getNumber(), fmt.format(level.getDiscount()));
                        }
                        confirmMarketingVO.setHalfPriceSecondPieceLevel(level);
                    }
                } else if (MarketingType.PREFERENTIAL.equals(tradeMarketingVO.getMarketingType())) {
                    List<MarketingPreferentialLevelVO> levels = tradeMarketingVO.getPreferentialLevelVOS();
                    if (CollectionUtils.isNotEmpty(levels)) {
                        if (MarketingSubType.PREFERENTIAL_FULL_AMOUNT.equals(tradeMarketingVO.getSubType())) {
                            desc = String.format("您已满足满%s元换购商品", fmt.format(levels.get(0).getFullAmount()));
                        } else {
                            desc = String.format("您已满足满%s件换购商品", levels.get(0).getFullCount());
                        }
                        Map<String, BigDecimal> skuNumMap = levels.stream()
                                .flatMap(g -> g.getPreferentialDetailList().stream())
                                .collect(Collectors.toMap(MarketingPreferentialGoodsDetailVO::getGoodsInfoId, MarketingPreferentialGoodsDetailVO::getPreferentialAmount));
                        confirmMarketingVO.setPreferentialSkuIds(Lists.newArrayList(skuNumMap.keySet()));
                        List<TradeItemVO> tradeItems = tradeConfirmItem.getPreferential()
                                .stream()
                                .filter(t -> tradeMarketing.getPreferentialSkuIds().contains(t.getSkuId())
                                        && Objects.equals(tradeMarketing.getMarketingId(), t.getMarketingIds().get(0)))
                                .collect(Collectors.toList());
                        BigDecimal preferentialDiscountsAmount = BigDecimal.ZERO;

                        for (TradeItemVO item : tradeItems){
                            BigDecimal price = skuNumMap.get(item.getSkuId());
                            preferentialDiscountsAmount =
                                    preferentialDiscountsAmount.add(item.getSplitPrice().subtract(price));
                            item.setSplitPrice(price);
                            item.getMarketingLevelIds().add(levels.get(0).getPreferentialLevelId());

                            //处理商品总价 订单总价
                            tradePriceVO.setGoodsPrice(tradePriceVO.getGoodsPrice().add(price));
                            tradePriceVO.setTotalPrice(tradePriceVO.getTotalPrice().add(price));
                            tradePriceVO.setOriginPrice(tradePriceVO.getOriginPrice().add(price));
                        }
                    }
                }
                if (!MarketingType.SUITS.equals(tradeMarketingVO.getMarketingType())) {
                    confirmMarketingVO.setMarketingDesc(desc);
                }
                tradeConfirmItem.getTradeConfirmMarketingList().add(confirmMarketingVO);
            }
        }
        tradeConfirmItem.setTradeConfirmMarketingList(tradeConfirmItem.getTradeConfirmMarketingList().stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TradeConfirmMarketingVO::getMarketingId))), Lists::newArrayList)));
        //应付金额 = 商品总金额 - 优惠总金额
        BigDecimal discountsPrice =
                tradeConfirmItem.getDiscountsPrice().stream().map(DiscountsVO::getAmount).reduce(BigDecimal.ZERO,
                        BigDecimal::add);
        // 加价购优惠金额不记入内
        tradePriceVO.setTotalPrice(tradePriceVO.getTotalPrice().subtract(discountsPrice));
    }

    public void clacSplitPrice(List<TradeItemVO> tradeItems, BigDecimal newTotal){
        if(CollectionUtils.isEmpty(tradeItems)){
            return;
        }
        // 1.如果新的总价为0或空，设置所有商品均摊价为0
        if (isNull(newTotal) || BigDecimal.ZERO.compareTo(newTotal) == 0) {
            tradeItems.forEach(tradeItem -> tradeItem.setSplitPrice(BigDecimal.ZERO));
            return;
        }
        // 计算商品旧的总价
        TradeItemVO tradeItemFirst = tradeItems.get(0);
        boolean flag = nonNull(tradeItemFirst.getIsBookingSaleGoods()) && tradeItemFirst.getIsBookingSaleGoods()
                && tradeItemFirst.getBookingType() == BookingType.EARNEST_MONEY && nonNull(tradeItemFirst.getTailPrice());
        BigDecimal total = flag ? tradeItemFirst.getTailPrice() : tradeItems.stream().map(TradeItemVO::getSplitPrice).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

        //内部总价为零或相等不用修改
        if(total.compareTo(newTotal) == 0){
            return;
        }
        // 尾款情况重新计算实际总价
        if (flag && total.compareTo(tradeItemFirst.getTailPrice()) == 0) {
            newTotal = tradeItemFirst.getEarnestPrice().add(newTotal);
            total = tradeItemFirst.getEarnestPrice().add(total);
        }

        int size = tradeItems.size();
        //累积平摊价，将剩余扣给最后一个元素
        BigDecimal splitPriceTotal = BigDecimal.ZERO;
        Long totalNum = tradeItems.stream().map(TradeItemVO::getNum).reduce(0L, Long::sum);

        for (int i = 0; i < size; i++) {
            TradeItemVO tradeItem = tradeItems.get(i);
            if (i == size - 1) {
                tradeItem.setSplitPrice(newTotal.subtract(splitPriceTotal));
            } else {
                BigDecimal splitPrice = ObjectUtils.defaultIfNull(tradeItem.getSplitPrice(), BigDecimal.ZERO);
                //全是零元商品按数量均摊
                if (BigDecimal.ZERO.compareTo(total) == 0) {
                    tradeItem.setSplitPrice(newTotal.multiply(BigDecimal.valueOf(tradeItem.getNum())).divide(BigDecimal.valueOf(totalNum), 2, RoundingMode.HALF_UP));
                } else {
                    tradeItem.setSplitPrice(splitPrice.divide(total, 10, RoundingMode.DOWN).multiply(newTotal).setScale(2, RoundingMode.HALF_UP));
                }
                splitPriceTotal = splitPriceTotal.add(tradeItem.getSplitPrice());
            }
        }
    }
    /**
     * 处理优惠券
     * @param param
     */
    public void dealCouponCodes(TradeConfirmParam param){
        Long storeId = param.getTradeItemSnapshot().getItemGroups().get(0).getSupplier().getStoreId();
        TradeConfirmResponse tradeConfirmResponse = param.getTradeConfirmResponse();
        List<TradeItemInfoDTO> tradeDtos = tradeConfirmResponse
                .getTradeConfirmItems()
                .stream()
                .map(TradeConfirmItemVO::getTradeItems)
                .flatMap(Collection::stream)
                .map(tradeItem -> TradeItemInfoDTO.builder()
                        .brandId(tradeItem.getBrand())
                        .cateId(tradeItem.getCateId())
                        .spuId(tradeItem.getSpuId())
                        .skuId(tradeItem.getSkuId())
                        .storeId(tradeItem.getStoreId())
                        .price(tradeItem.getSplitPrice())
                        .distributionGoodsAudit(tradeItem.getDistributionGoodsAudit())
                        .build())
                .collect(Collectors.toList());
        List<TradeItemInfoDTO> preferentialTradeDtos = tradeConfirmResponse
                .getTradeConfirmItems()
                .stream()
                .map(TradeConfirmItemVO::getPreferential)
                .flatMap(Collection::stream)
                .map(tradeItem -> TradeItemInfoDTO.builder()
                        .brandId(tradeItem.getBrand())
                        .cateId(tradeItem.getCateId())
                        .spuId(tradeItem.getSpuId())
                        .skuId(tradeItem.getSkuId())
                        .storeId(tradeItem.getStoreId())
                        .price(tradeItem.getSplitPrice())
                        .distributionGoodsAudit(tradeItem.getDistributionGoodsAudit())
                        .build())
                .collect(Collectors.toList());

        Map<String, TradeItemInfoDTO> TradeItemInfoDTOMap =
                tradeDtos.stream().collect(Collectors.toMap(TradeItemInfoDTO::getSkuId,
                Function.identity()));
        preferentialTradeDtos.forEach(t -> {
            TradeItemInfoDTO tradeItemInfoDTO = TradeItemInfoDTOMap.get(t.getSkuId());
            if (Objects.nonNull(tradeItemInfoDTO)){
                tradeItemInfoDTO.setPrice(tradeItemInfoDTO.getPrice().add(t.getPrice()));
            } else {
                TradeItemInfoDTOMap.put(t.getSkuId(), t);
            }
        });
        List<TradeItemInfoDTO> newTradeDtos = new ArrayList<>(TradeItemInfoDTOMap.values());
        CouponCodeListForUseByCustomerIdRequest requ = CouponCodeListForUseByCustomerIdRequest.builder()
                .customerId(param.getCustomerVO().getCustomerId())
                .terminalToken(param.getTradeItemSnapshot().getTerminalToken())
                .tradeItems(newTradeDtos)
                .price(tradeConfirmResponse.getTotalPrice())
                .storeId(storeId)
                .build();
        List<CouponCodeVO> couponCodes = Optional.ofNullable(this.couponCodeQueryProvider.listForUseByCustomerId(requ))
                .map(BaseResponse::getContext)
                .map(CouponCodeListForUseByCustomerIdResponse::getCouponCodeList)
                .orElseGet(Lists::newArrayList);
//        tradeConfirmResponse.setCouponCodes(couponCodes);
        // 可用优惠券列表
        List<CouponCodeVO> availableCoupons = couponCodes.stream()
                .filter(couponCodeVO -> CouponCodeStatus.AVAILABLE.equals(couponCodeVO.getStatus())).collect(Collectors.toList());
        // 区分满系券（满减、满折）可用数量
        tradeConfirmResponse.setCouponAvailableCount((int) availableCoupons.stream().filter(CouponCodeVO::isFullAmount).count());
        // 区分运费券可用数量
        tradeConfirmResponse.setFreightCouponAvailableCount((int) availableCoupons.stream().filter(CouponCodeVO::isFreight).count());
    }

    /**
     * @description   处理用户可用礼品卡数据
     * @author  wur
     * @date: 2022/12/15 19:29
     * @param param
     * @return
     **/
    public void dealGiftCard(TradeConfirmParam param) {
        TradeConfirmResponse tradeConfirmResponse = param.getTradeConfirmResponse();
        List<TradeConfirmItemVO> tradeConfirmItems = tradeConfirmResponse.getTradeConfirmItems();
        FindTradeSnapshotRequest request = param.getFindTradeSnapshotRequest();
        if (Objects.isNull(tradeConfirmResponse)
                || Objects.isNull(request)) {
            return ;
        }
        // 封装快照中商品信息
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        tradeConfirmItems.forEach(tradeItemGroupVO -> {
            tradeItemGroupVO.getTradeItems().forEach(tradeItemVO -> {
                GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                goodsInfoVO.setGoodsInfoId(tradeItemVO.getSkuId());
                goodsInfoVO.setStoreId(tradeItemVO.getStoreId());
                goodsInfoVO.setCateId(tradeItemVO.getCateId());
                goodsInfoVO.setBrandId(tradeItemVO.getBrand());
                goodsInfoVOList.add(goodsInfoVO);
            });
            if (CollectionUtils.isNotEmpty(tradeItemGroupVO.getPreferential())) {
                tradeItemGroupVO.getPreferential().forEach(tradeItemVO -> {
                    GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                    goodsInfoVO.setGoodsInfoId(tradeItemVO.getSkuId());
                    goodsInfoVO.setStoreId(tradeItemVO.getStoreId());
                    goodsInfoVO.setCateId(tradeItemVO.getCateId());
                    goodsInfoVO.setBrandId(tradeItemVO.getBrand());
                    goodsInfoVOList.add(goodsInfoVO);
                });
            }
        });

        UserGiftCardTradeRequest giftCardTradeRequest = new UserGiftCardTradeRequest();
        giftCardTradeRequest.setCustomerId(request.getCustomerId());
        giftCardTradeRequest.setGoodsInfoVOList(goodsInfoVOList);
        UserGiftCardTradeResponse response = userGiftCardProvider.tradeUserGiftCard(giftCardTradeRequest).getContext();
        tradeConfirmResponse.setGiftCardNum(response.getValidNum());
    }
}
