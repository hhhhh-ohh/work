package com.wanmi.sbc.order.optimization.trade1.commit.service;

import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.trade.model.entity.TradeCommitResult;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;

import java.util.List;

/**
 * @author zhanggaolei
 */
public interface Trade1CommitAfterInterface {
    /**
     * 发送短信
     */
    void sendMessage(List<Trade> tradeList);

    /**
     * 移除购物车商品
     */
    void removePurchaseGoodsInfo(List<Trade> tradeList, TradeItemSnapshot snapshot, TradeCommitRequest request);

    /**
     * 删除订单快照
     */
    void removeSnapshot(String terminalToken);

    /**
     * 增加限售记录
     */
    void addRestrictedRecord(List<Trade> tradeList);

    /**
     * 订单送积分处理
     */
    void dealOrderPointsIncrease(List<Trade> tradeList);

    /**
     * 返回参数处理
     */
    List<TradeCommitResult> returnProcess(List<Trade> tradeList);

    /**
     * 删除周期购缓存
     */
    void removeCycleSnapshot(List<Trade> tradeList, String terminalToken);
}
