package com.wanmi.sbc.order.trade.fsm.action;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.trade.fsm.TradeAction;
import com.wanmi.sbc.order.trade.fsm.TradeStateContext;
import com.wanmi.sbc.order.trade.fsm.params.StateRequest;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.TradeEventLog;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */
@Component
public class ConfirmAction extends TradeAction {

    @Override
    protected void evaluateInternal(Trade trade, StateRequest request, TradeStateContext tsc) {
        TradeState tradeState = trade.getTradeState();

        if (!(tradeState.getDeliverStatus() == DeliverStatus.SHIPPED)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050017);
        }

        //
        tradeState.setFlowState(FlowState.CONFIRMED);

        //
        String detail = String.format("订单%s已确认收货", trade.getId());
        trade.appendTradeEventLog(TradeEventLog
                .builder()
                .operator(tsc.getOperator())
                .eventType(FlowState.CONFIRMED.getDescription())
                .eventDetail(detail)
                .eventTime(LocalDateTime.now())
                .build());
        save(trade);


        List<ProviderTrade> providerTradeList = providerTradeService.findListByParentId(trade.getId());
        for (ProviderTrade providerTrade : providerTradeList){
            if(providerTrade.getTradeState()!=null){
                providerTrade.getTradeState().setFlowState(FlowState.CONFIRMED);
                providerTrade.getTradeState().setEndTime(LocalDateTime.now());
                saveProviderTrade(providerTrade);
            }
        }


        super.operationLogMq.convertAndSend(tsc.getOperator(), FlowState.COMPLETED.getDescription(), detail);
    }
}
