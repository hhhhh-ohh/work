package com.wanmi.sbc.order.provider.impl.optimization;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.optimization.Trade1Provider;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.response.trade.TradeCommitResponse;
import com.wanmi.sbc.order.optimization.trade1.commit.TradeCommitDispatch;
import com.wanmi.sbc.order.trade.model.entity.TradeCommitResult;
import com.wanmi.sbc.order.util.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author zhanggaolei
 * @className Trade1Provider
 * @description TODO
 * @date 2022/3/16 4:14 下午
 */
@RestController
public class Trade1Controller implements Trade1Provider {

    @Autowired TradeCommitDispatch tradeCommitDispatch;

    @Autowired OrderMapper orderMapper;

    @Override
    public BaseResponse<TradeCommitResponse> commit(
            @RequestBody @Valid TradeCommitRequest tradeCommitRequest) {
        TradeCommitResponse response = new TradeCommitResponse();
        try {
            List<TradeCommitResult> result = tradeCommitDispatch.commit(tradeCommitRequest);
            response.setTradeCommitResults(
                    orderMapper.tradeCommitResultsToTradeCommitResultVOs(result));
            tradeCommitDispatch.commitSuccessDelayProcess(tradeCommitRequest);
        } catch (Exception e) {
            tradeCommitDispatch.commitErrorDelayProcess(tradeCommitRequest);
            throw e;
        }
        return BaseResponse.success(response);
    }
}
