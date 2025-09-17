package com.wanmi.sbc.order.optimization.trade1.commit.service;

import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.vo.TradeCommitResultVO;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.trade.model.entity.TradeCommitResult;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;

import java.util.List;

/**
 * @author zhanggaolei
 */
public interface Trade1CommitInterface {

    /**
     * 请求参数处理
     */
    TradeCommitRequest processRequest(TradeCommitRequest request);

    /**
     * 获取生成订单所需数据
     */
    Trade1CommitParam getData(TradeCommitRequest request);

    /**
     * 订单信息校验
     */
    void check(Trade1CommitParam param,TradeCommitRequest request);

    /**
     * 处理订单信息
     */
    List<Trade> process(Trade1CommitParam param, TradeCommitRequest request);

    /**
     * 生成订单信息
     */
    List<Trade> create(List<Trade> tradeList,TradeCommitRequest request);


    /**
     * 订单之后的处理逻辑
     */
    List<TradeCommitResult> afterCommit(List<Trade> tradeList, TradeCommitRequest request, TradeItemSnapshot snapshot);
}
