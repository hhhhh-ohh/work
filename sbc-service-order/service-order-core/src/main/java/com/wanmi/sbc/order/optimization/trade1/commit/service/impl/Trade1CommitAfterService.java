package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.restrictedrecord.RestrictedRecordSaveProvider;
import com.wanmi.sbc.goods.api.request.restrictedrecord.RestrictedRecordBatchAddRequest;
import com.wanmi.sbc.goods.bean.vo.RestrictedRecordSimpVO;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.enums.AuditState;
import com.wanmi.sbc.order.message.StoreMessageBizService;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitAfterInterface;
import com.wanmi.sbc.order.purchase.PurchaseService;
import com.wanmi.sbc.order.purchase.request.PurchaseRequest;
import com.wanmi.sbc.order.trade.model.entity.TradeCommitResult;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeBuyCycleService;
import com.wanmi.sbc.order.trade.service.TradeCommitIncision;
import com.wanmi.sbc.order.trade.service.TradeItemService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className Trade1CommitAfterService
 * @description TODO
 * @date 2022/3/1 3:40 下午
 */
@Service
public class Trade1CommitAfterService implements Trade1CommitAfterInterface {

    @Autowired TradeCommitIncision tradeCommitIncision;

    @Autowired OrderProducerService orderProducerService;

    @Autowired PurchaseService purchaseService;

    @Autowired TradeItemService tradeItemService;

    @Autowired RestrictedRecordSaveProvider restrictedRecordSaveProvider;

    @Autowired StoreMessageBizService storeMessageBizService;

    @Autowired
    TradeBuyCycleService tradeBuyCycleService;

    @Autowired
    UserGiftCardProvider userGiftCardProvider;

    @Override
    public void sendMessage(List<Trade> tradeList) {
        // 发送订单进度短信
        tradeList.stream()
                .filter(trade -> !(AuditState.REJECTED == trade.getTradeState().getAuditState()))
                .forEach(trade -> {
                    orderProducerService.sendTradeMessage(trade);
                    // 订单待审核提醒
                    storeMessageBizService.handleForTradeWaitAudit(trade);
                    // 订单待发货提醒
                    storeMessageBizService.handleForTradeWaitDeliver(trade, false);
                    // 商家库存预警
                    storeMessageBizService.handleForTradeWaitStock(trade);
                });
    }

    @Override
    public void removePurchaseGoodsInfo(
            List<Trade> tradeList, TradeItemSnapshot snapshot, TradeCommitRequest request) {
        if (Boolean.TRUE.equals(snapshot.getPurchaseBuy())) {
            List<String> tradeSkuIds =
                    tradeList.stream()
                            .flatMap(trade -> trade.getTradeItems().stream())
                            .map(TradeItem::getSkuId)
                            .collect(Collectors.toList());
            String inviteeId = Constants.PURCHASE_DEFAULT;
            if (null != request.getDistributeChannel()
                    && Objects.equals(
                            request.getDistributeChannel().getChannelType(), ChannelType.SHOP)) {
                inviteeId = request.getDistributeChannel().getInviteeId();
            }
            PurchaseRequest purchaseRequest =
                    PurchaseRequest.builder()
                            .customerId(request.getOperator().getUserId())
                            .goodsInfoIds(tradeSkuIds)
                            .inviteeId(inviteeId)
                            .build();
            purchaseService.delete(purchaseRequest);
        }
    }

    @Override
    public void removeSnapshot(String terminalToken) {
        tradeItemService.remove(terminalToken);
    }

    @Override
    public void addRestrictedRecord(List<Trade> tradeList) {
        if (CollectionUtils.isEmpty(tradeList)) {
            return;
        }
        Trade trade = tradeList.get(0);
        String customerId = trade.getBuyer().getId();
        LocalDateTime orderTime = LocalDateTime.now();
        List<RestrictedRecordSimpVO> restrictedRecordSimpVOS =
                tradeList.stream()
                        .filter(
                                t ->
                                        !((Objects.nonNull(t.getGrouponFlag())
                                                        && t.getGrouponFlag())
                                                || Objects.nonNull(t.getIsFlashSaleGoods())
                                                        && t.getIsFlashSaleGoods()
                                                || (Objects.nonNull(t.getStoreBagsFlag())
                                                        && DefaultFlag.YES.equals(
                                                                t.getStoreBagsFlag()))
                                                || (Objects.nonNull(t.getSuitMarketingFlag())
                                                        && t.getSuitMarketingFlag())))
                        .flatMap(t -> t.getTradeItems().stream())
                        .map(
                                tradeItem ->
                                        KsBeanUtil.convert(tradeItem, RestrictedRecordSimpVO.class))
                        .collect(Collectors.toList());
        StoreType storeType = trade.getSupplier().getStoreType();
        Long storeId = null;
        if (StoreType.O2O == storeType) {
            storeId = trade.getSupplier().getStoreId();
        }
        restrictedRecordSaveProvider.batchAdd(
                RestrictedRecordBatchAddRequest.builder()
                        .restrictedRecordSimpVOS(restrictedRecordSimpVOS)
                        .customerId(customerId)
                        .storeId(storeId)
                        .orderTime(orderTime)
                        .build());
    }

    @Override
    public void dealOrderPointsIncrease(List<Trade> tradeList) {
        orderProducerService.sendOrderPointsIncrease(tradeList);
    }

    @Override
    public List<TradeCommitResult> returnProcess(List<Trade> tradeList) {
        List<TradeCommitResult> resultList = new ArrayList<>();
        tradeList.forEach(
                trade -> {
                    // 拆单标识
                    boolean splitFlag = tradeCommitIncision.resultPluginType(trade);
                    if (Objects.nonNull(trade.getIsBookingSaleGoods())
                            && trade.getIsBookingSaleGoods()
                            && Objects.nonNull(trade.getBookingType())
                            && trade.getBookingType() == BookingType.EARNEST_MONEY) {
                        resultList.add(
                                new TradeCommitResult(
                                        trade.getId(),
                                        trade.getParentId(),
                                        trade.getTradeState(),
                                        trade.getPaymentOrder(),
                                        trade.getTradePrice().getEarnestPrice(),
                                        trade.getOrderTimeOut(),
                                        trade.getSupplier().getStoreName(),
                                        trade.getSupplier().getIsSelf(),
                                        splitFlag,trade.getSupplier().getStoreType()));
                    } else {
                        resultList.add(
                                new TradeCommitResult(
                                        trade.getId(),
                                        trade.getParentId(),
                                        trade.getTradeState(),
                                        trade.getPaymentOrder(),
                                        trade.getTradePrice().getTotalPrice(),
                                        trade.getOrderTimeOut(),
                                        trade.getSupplier().getStoreName(),
                                        trade.getSupplier().getIsSelf(),
                                        splitFlag,trade.getSupplier().getStoreType()));
                    }
                });
        return resultList;
    }

    @Override
    public void removeCycleSnapshot(List<Trade> tradeList, String terminalToken) {
        Optional<Trade> optional = tradeList.stream().filter(trade ->
                        Objects.nonNull(trade.getOrderTag()) && Boolean.TRUE.equals(trade.getOrderTag().getBuyCycleFlag()))
                .findAny();
        if (optional.isPresent()) {
            tradeBuyCycleService.removeSnapshot(terminalToken);
        }
    }
}
