package com.wanmi.sbc.order.optimization.trade1.commit.service;

import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.trade.model.root.Trade;

import java.util.List;

/**
 * @author zhanggaolei
 */
public interface Trade1CommitCreateInterface {
    /**
     * 创建拼团订单
     */
    void createGrouponInstance(List<Trade> tradeList);

    void createPayOrder(List<Trade> tradeList, TradeCommitRequest request);

    void createTradeGroup(List<Trade> tradeList, TradeCommitRequest request);

    void minusStock(List<Trade> tradeList);

    void minusGiftStock(List<Trade> tradeList);

    void minusGrouponStock(List<Trade> tradeList);

    void minusBookingSaleStock(List<Trade> tradeList);

    void minusPoint(List<Trade> tradeList);

    void minusCoupon(List<Trade> tradeList, TradeCommitRequest request);

    void createTrade(List<Trade> tradeList,TradeCommitRequest request);

    void createThirdPlatformTrade(List<Trade> tradeList);

    void createSellPlatformTrade(List<Trade> tradeList, TradeCommitRequest request);

    void createTimeOutMQ(List<Trade> tradeList);
}
