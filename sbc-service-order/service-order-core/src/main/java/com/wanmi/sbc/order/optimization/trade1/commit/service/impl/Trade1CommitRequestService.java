package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitCheckInterface;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitRequestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanggaolei
 * @className Trade1CommitRequestService
 * @description TODO
 * @date 2022/3/1 3:34 下午
 */
@Service
public class Trade1CommitRequestService implements Trade1CommitRequestInterface {
    @Autowired Trade1CommitCheckInterface trade1CommitCheckInterface;

    @Override
    public TradeCommitRequest setRequest(TradeCommitRequest request) {
        return request;
    }

    @Override
    public TradeCommitRequest  checkRequest(TradeCommitRequest request) {
        // 校验支付方式
        trade1CommitCheckInterface.checkPayType(
                request.getStoreCommitInfoList().get(0).getPayType());
        return request;
    }
}
