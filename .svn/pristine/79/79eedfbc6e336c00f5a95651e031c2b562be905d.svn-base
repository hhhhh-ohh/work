package com.wanmi.sbc.optimization;

import com.wanmi.sbc.common.annotation.MultiSubmitWithToken;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.api.provider.optimization.Trade1Provider;
import com.wanmi.sbc.order.api.provider.trade.TradeItemProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeItemQueryProvider;
import com.wanmi.sbc.order.api.request.trade.BuyCycleInfoRequest;
import com.wanmi.sbc.order.api.request.trade.FindTradeSnapshotRequest;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.request.trade.TradeItemSnapshotByCustomerIdRequest;
import com.wanmi.sbc.order.api.response.trade.BuyCycleInfoResponse;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.order.bean.enums.BuyType;
import com.wanmi.sbc.order.bean.vo.TradeCommitResultVO;
import com.wanmi.sbc.order.bean.vo.TradeItemSnapshotVO;
import com.wanmi.sbc.order.request.*;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className Trade1BaseController
 * @description
 * @date 2022/4/1 16:04
 */
@Tag(name = "Trade1BaseController", description = "订单性能优化")
@RestController
@RequestMapping("/optimization/trade")
@Slf4j
@Validated
public class Trade1BaseController {

    @Autowired Trade1Provider trade1Provider;

    @Autowired CommonUtil commonUtil;

    @Autowired RedissonClient redissonClient;

    @Autowired TradeItemProvider tradeItemProvider;

    @Autowired CommunityActivityService communityActivityService;

    @Autowired
    TradeItemQueryProvider tradeItemQueryProvider;


    /** 提交订单，用于生成订单操作 */
    @Operation(summary = "提交订单，用于生成订单操作")
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @MultiSubmitWithToken
    public BaseResponse<List<TradeCommitResultVO>> optimizationCommit(
            @RequestBody @Valid TradeCommitRequest tradeCommitRequest) {
        DistributeChannel distributeChannel = commonUtil.getDistributeChannel();
        Operator operator = commonUtil.getOperator();
        tradeCommitRequest.setDistributeChannel(distributeChannel);
        tradeCommitRequest.setOperator(operator);
        tradeCommitRequest.setTerminalToken(commonUtil.getTerminalToken());
        // 是否开启第三方平台
        boolean isOpen = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        tradeCommitRequest.setIsOpen(isOpen);

        TradeItemSnapshotVO tradeItemSnapshotVO =
                tradeItemQueryProvider
                        .listByTerminalToken(
                                TradeItemSnapshotByCustomerIdRequest.builder()
                                        .terminalToken(commonUtil.getTerminalToken())
                                        .build())
                        .getContext()
                        .getTradeItemSnapshotVO();
        RLock rLock = redissonClient.getFairLock(operator.getUserId());
        if (Objects.nonNull(tradeItemSnapshotVO)) {
            boolean groupFlag =
                    tradeItemSnapshotVO.getItemGroups().stream()
                            .anyMatch(a -> Objects.nonNull(a.getGrouponForm()));
            if (groupFlag
                    && Objects.nonNull(
                            tradeItemSnapshotVO
                                    .getItemGroups()
                                    .get(0)
                                    .getGrouponForm()
                                    .getGrouponNo())) {
                String key =
                        tradeItemSnapshotVO
                                .getItemGroups()
                                .get(0)
                                .getGrouponForm()
                                .getGrouponActivityId()
                                .concat(
                                        tradeItemSnapshotVO
                                                .getItemGroups()
                                                .get(0)
                                                .getGrouponForm()
                                                .getGrouponNo());
                rLock = redissonClient.getFairLock(key);
            }
        }
        rLock.lock();
        List<TradeCommitResultVO> successResults;
        try {
            successResults =
                    trade1Provider.commit(tradeCommitRequest).getContext().getTradeCommitResults();
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.success(successResults);
    }

    @Operation(summary = "立即购买")
    @RequestMapping(value = "/immediate-buy", method = RequestMethod.POST)
    public BaseResponse immediateBuy(@RequestBody @Valid ImmediateBuyRequest request) {
        TradeBuyRequest tradeBuyRequest = KsBeanUtil.convert(request, TradeBuyRequest.class);
        tradeBuyRequest.setCustomerId(commonUtil.getOperatorId());
        tradeBuyRequest.setTerminalToken(commonUtil.getTerminalToken());
       // EnterpriseCheckState customerState = commonUtil.getCustomer().getEnterpriseCheckState();
        boolean isIepCustomer =
                commonUtil.isIepCustomer()
                        && commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING);
        tradeBuyRequest.setIepCustomerFlag(isIepCustomer);
        tradeBuyRequest.setBuyType(BuyType.IMMEDIATE_BUY);
        return tradeItemProvider.snapshotNew(tradeBuyRequest);
    }

    @Operation(summary = "砍价商品购买")
    @RequestMapping(value = "/bargainBuy", method = RequestMethod.POST)
    public BaseResponse bargainBuy(@RequestBody @Valid BargainBuyRequest bargainBuyRequest) {
        TradeBuyRequest tradeBuyRequest = KsBeanUtil.convert(bargainBuyRequest, TradeBuyRequest.class);
        tradeBuyRequest.setCustomerId(commonUtil.getOperatorId());
        tradeBuyRequest.setTerminalToken(commonUtil.getTerminalToken());
        EnterpriseCheckState customerState = commonUtil.getCustomer().getEnterpriseCheckState();
        boolean isIepCustomer =
                Objects.nonNull(customerState)
                        && customerState == EnterpriseCheckState.CHECKED
                        && commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING);
        tradeBuyRequest.setIepCustomerFlag(isIepCustomer);
        tradeBuyRequest.setBuyType(BuyType.BARGAIN);
        return tradeItemProvider.snapshotNew(tradeBuyRequest);
    }

    @Operation(summary = "开店礼包购买")
    @RequestMapping(value = "/store-bags-buy", method = RequestMethod.POST)
    public BaseResponse storeBagsBuy(@RequestBody @Valid StoreBagsBuyRequest request) {
        TradeBuyRequest tradeBuyRequest = new TradeBuyRequest();
        com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest tradeItemRequest =
                new com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest();
        tradeItemRequest.setNum(1L);
        tradeItemRequest.setSkuId(request.getGoodsInfoId());
        tradeBuyRequest.setTradeItemRequests(Collections.singletonList(tradeItemRequest));
        tradeBuyRequest.setCustomerId(commonUtil.getOperatorId());
        tradeBuyRequest.setTerminalToken(commonUtil.getTerminalToken());
        tradeBuyRequest.setBuyType(BuyType.STORE_BAGS_BUY);
        return tradeItemProvider.snapshotNew(tradeBuyRequest);
    }

    @Operation(summary = "组合购购买")
    @RequestMapping(value = "/suits-buy", method = RequestMethod.POST)
    public BaseResponse suitsBuy(@RequestBody @Valid SuitBuyRequest request) {
        TradeBuyRequest tradeBuyRequest = new TradeBuyRequest();
        tradeBuyRequest.setMarketingId(request.getMarketingId());
        tradeBuyRequest.setCustomerId(commonUtil.getOperatorId());
        tradeBuyRequest.setTerminalToken(commonUtil.getTerminalToken());
        tradeBuyRequest.setBuyType(BuyType.SUIT_BUY);
        return tradeItemProvider.snapshotNew(tradeBuyRequest);
    }

    /**
     * 拼团购买
     */
    @Operation(summary = "拼团购买")
    @RequestMapping(value = "/groupon-buy", method = RequestMethod.POST)
    @MultiSubmitWithToken
    public BaseResponse grouponBuyNew(@RequestBody @Valid GrouponBuyRequest request) {
        TradeBuyRequest snapshotRequest = new TradeBuyRequest();
        snapshotRequest.setCustomerId(commonUtil.getOperatorId());
        snapshotRequest.setSkuId(request.getGoodsInfoId());
        snapshotRequest.setNum(request.getBuyCount());
        snapshotRequest.setOpenGroupon(request.getOpenGroupon());
        snapshotRequest.setGrouponNo(request.getGrouponNo());
        snapshotRequest.setTerminalToken(commonUtil.getTerminalToken());
        snapshotRequest.setShareUserId(request.getShareUserId());
        snapshotRequest.setBuyType(BuyType.GROUP_BUY);
        boolean isIepCustomer =
                commonUtil.isIepCustomer()
                        && commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING);
        snapshotRequest.setIepCustomerFlag(isIepCustomer);
        snapshotRequest.setDistributeChannel(commonUtil.getDistributeChannel());
        // 填充、生成快照
        return tradeItemProvider.groupBuySnapshot(snapshotRequest);
    }

    /**
     * 从采购单中确认订单商品
     */
    @Operation(summary = "从采购单中确认订单商品")
    @RequestMapping(value = "/confirm", method = RequestMethod.PUT)
    @MultiSubmitWithToken
    public BaseResponse confirm(@RequestBody @Valid TradeItemConfirmRequest confirmRequest) {
        List<com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest> tradeItems = confirmRequest.getTradeItems().stream().map(
                tradeItem -> {
                    com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest tradeItemRequest = new com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest();
                    tradeItemRequest.setSkuId(tradeItem.getSkuId());
                    tradeItemRequest.setNum(tradeItem.getNum());
                    tradeItemRequest.setIsAppointmentSaleGoods(tradeItem.getIsAppointmentSaleGoods());
                    tradeItemRequest.setAppointmentSaleId(tradeItem.getAppointmentSaleId());
                    tradeItemRequest.setIsBookingSaleGoods(tradeItem.getIsBookingSaleGoods());
                    tradeItemRequest.setBookingSaleId(tradeItem.getBookingSaleId());
                    return tradeItemRequest;
                }
        ).collect(Collectors.toList());

        DistributeChannel distributeChannel = commonUtil.getDistributeChannel();
        //  非小店模式默认0 与加入购物车时获取分销员的邀请人id方式保持一致
        if (Objects.nonNull(distributeChannel) && !Objects.equals(distributeChannel.getChannelType(), ChannelType.SHOP)) {
            distributeChannel.setInviteeId(Constants.PURCHASE_DEFAULT);
        }
        TradeBuyRequest request = new TradeBuyRequest();
        request.setCustomerId(commonUtil.getOperatorId());
        request.setDistributeChannel(distributeChannel);
        request.setTradeItemRequests(tradeItems);
        request.setTradeMarketingList(confirmRequest.getTradeMarketingList());
        request.setForceConfirm(confirmRequest.isForceConfirm());
        request.setTerminalToken(commonUtil.getTerminalToken());
        request.setAreaId(confirmRequest.getAreaId());
        request.setStoreId(confirmRequest.getStoreId());
        request.setAddressId(confirmRequest.getAddressId());
        request.setBuyType(BuyType.CONFIRM_BUY);
        boolean isIepCustomer =
                commonUtil.isIepCustomer()
                        && commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING);
        request.setIepCustomerFlag(isIepCustomer);
        return tradeItemProvider.confirmSnapshot(request);
    }

    /**
     * 用于确认订单后，创建订单前的获取订单商品信息
     *
     * 如果商品是
     *
     */
    @Operation(summary = "用于确认订单后，创建订单前的获取订单商品信息")
    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
    public BaseResponse<TradeConfirmResponse> getPurchaseItems() {
        FindTradeSnapshotRequest request = FindTradeSnapshotRequest.builder()
                .customerId(commonUtil.getOperatorId())
                .terminalToken(commonUtil.getTerminalToken())
                .distributeChannel(commonUtil.getDistributeChannel())
                .vasIpeFlag(commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING))
                .build();
        return tradeItemProvider.findTradeSnapshot(request);
    }

    @Operation(summary = "周期购购买")
    @MultiSubmitWithToken
    @RequestMapping(value = "/cycle-buy", method = RequestMethod.POST)
    public BaseResponse cycleBuy(@RequestBody @Valid CycleBuyRequest request) {
        TradeBuyRequest tradeBuyRequest = new TradeBuyRequest();
        TradeItemRequest itemRequest = new TradeItemRequest();
        itemRequest.setNum(request.getNum());
        itemRequest.setSkuId(request.getSkuId());
        itemRequest.setMarketingId(request.getMarketingId());
        tradeBuyRequest.setTradeItemRequests(Collections.singletonList(itemRequest));
        tradeBuyRequest.setDeliveryCycleNum(request.getDeliveryCycleNum());
        tradeBuyRequest.setDeliveryDate(request.getDeliveryDate());
        tradeBuyRequest.setDeliveryDateList(request.getDeliveryDateList());
        tradeBuyRequest.setCustomerId(commonUtil.getOperatorId());
        tradeBuyRequest.setTerminalToken(commonUtil.getTerminalToken());
        boolean isIepCustomer =
                commonUtil.isIepCustomer()
                        && commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING);
        tradeBuyRequest.setIepCustomerFlag(isIepCustomer);
        tradeBuyRequest.setBuyType(BuyType.CYCLE_BUY);
        return tradeItemProvider.snapshotNew(tradeBuyRequest);
    }


    @Operation(summary = "周期计划")
    @GetMapping(value = "/cycle/deliveryPlan/{skuId}")
    public BaseResponse<BuyCycleInfoResponse> cycleDeliveryPlan(@PathVariable String skuId) {
        BuyCycleInfoRequest request = new BuyCycleInfoRequest();
        request.setSkuId(skuId);
        request.setCustomerId(commonUtil.getOperatorId());
        request.setTerminalToken(commonUtil.getTerminalToken());
        return tradeItemProvider.buyCycleInfo(request);
    }


    /**
     * 快速下单确认订单商品
     */
    @Operation(summary = "快速下单确认订单商品")
    @RequestMapping(value = "/quickOrder/confirm", method = RequestMethod.PUT)
    @MultiSubmitWithToken
    public BaseResponse quickOrderConfirm(@RequestBody @Valid TradeItemConfirmRequest confirmRequest) {
        List<com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest> tradeItems = confirmRequest.getTradeItems().stream().map(
                tradeItem -> {
                    com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest tradeItemRequest = new com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest();
                    tradeItemRequest.setSkuId(tradeItem.getSkuId());
                    tradeItemRequest.setNum(tradeItem.getNum());
                    tradeItemRequest.setIsAppointmentSaleGoods(tradeItem.getIsAppointmentSaleGoods());
                    tradeItemRequest.setAppointmentSaleId(tradeItem.getAppointmentSaleId());
                    tradeItemRequest.setIsBookingSaleGoods(tradeItem.getIsBookingSaleGoods());
                    tradeItemRequest.setBookingSaleId(tradeItem.getBookingSaleId());
                    return tradeItemRequest;
                }
        ).collect(Collectors.toList());

        DistributeChannel distributeChannel = commonUtil.getDistributeChannel();
        //  非小店模式默认0 与加入购物车时获取分销员的邀请人id方式保持一致
        if (Objects.nonNull(distributeChannel) && !Objects.equals(distributeChannel.getChannelType(), ChannelType.SHOP)) {
            distributeChannel.setInviteeId(Constants.PURCHASE_DEFAULT);
        }
        TradeBuyRequest request = new TradeBuyRequest();
        request.setCustomerId(commonUtil.getOperatorId());
        request.setDistributeChannel(distributeChannel);
        request.setTradeItemRequests(tradeItems);
        request.setTradeMarketingList(confirmRequest.getTradeMarketingList());
        request.setForceConfirm(confirmRequest.isForceConfirm());
        request.setTerminalToken(commonUtil.getTerminalToken());
        request.setAreaId(confirmRequest.getAreaId());
        request.setStoreId(confirmRequest.getStoreId());
        request.setAddressId(confirmRequest.getAddressId());
        request.setBuyType(BuyType.QUICK_ORDER_BUY);
        boolean isIepCustomer =
                commonUtil.isIepCustomer()
                        && commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING);
        request.setIepCustomerFlag(isIepCustomer);
        return tradeItemProvider.quickOrderSnapshot(request);
    }

    @Operation(summary = "提货卡兑换")
    @RequestMapping(value = "/pickup-card-buy", method = RequestMethod.POST)
    public BaseResponse pickupCardBuy(@RequestBody @Valid PickupCardBuyRequest request) {
        List<TradeItemRequest> tradeItems = new ArrayList<>();
        request.getGoodsInfoIds().forEach(goodInfoId -> {
            TradeItemRequest tradeItemRequest = new TradeItemRequest();
            tradeItemRequest.setNum(1L);
            tradeItemRequest.setSkuId(goodInfoId);
            tradeItems.add(tradeItemRequest);
        });

        TradeBuyRequest tradeBuyRequest = new TradeBuyRequest();
        tradeBuyRequest.setCustomerId(commonUtil.getOperatorId());
        tradeBuyRequest.setTradeItemRequests(tradeItems);
        tradeBuyRequest.setTerminalToken(commonUtil.getTerminalToken());
        tradeBuyRequest.setUserGiftCardId(request.getUserGiftCardId());
        tradeBuyRequest.setBuyType(BuyType.PICKUP_CARD_BUY);
        return tradeItemProvider.confirmSnapshot(tradeBuyRequest);
    }

    @Operation(summary = "社区团购快照")
    @RequestMapping("/community/confirm")
    public BaseResponse communityConfirm(@RequestBody @Valid TradeItemConfirmRequest confirmRequest) {
        communityActivityService.checkOpen();
        List<TradeItemRequest> tradeItems = confirmRequest.getTradeItems().stream().map(
                tradeItem -> {
                    TradeItemRequest tradeItemRequest = new TradeItemRequest();
                    tradeItemRequest.setSkuId(tradeItem.getSkuId());
                    tradeItemRequest.setNum(tradeItem.getNum());
                    tradeItemRequest.setIsAppointmentSaleGoods(tradeItem.getIsAppointmentSaleGoods());
                    tradeItemRequest.setAppointmentSaleId(tradeItem.getAppointmentSaleId());
                    tradeItemRequest.setIsBookingSaleGoods(tradeItem.getIsBookingSaleGoods());
                    tradeItemRequest.setBookingSaleId(tradeItem.getBookingSaleId());
                    return tradeItemRequest;
                }
        ).collect(Collectors.toList());

        TradeBuyRequest request = new TradeBuyRequest();
        request.setCustomerId(commonUtil.getOperatorId());
        request.setTradeItemRequests(tradeItems);
        request.setTradeMarketingList(confirmRequest.getTradeMarketingList());
        request.setTerminalToken(commonUtil.getTerminalToken());
        request.setStoreId(confirmRequest.getStoreId());
        request.setBuyType(BuyType.COMMUNITY_BUY);
        request.setCommunityBuyRequest(confirmRequest.getCommunityBuyRequest());
        boolean isIepCustomer =
                commonUtil.isIepCustomer()
                        && commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING);
        request.setIepCustomerFlag(isIepCustomer);
        return tradeItemProvider.confirmSnapshot(request);
    }
}
