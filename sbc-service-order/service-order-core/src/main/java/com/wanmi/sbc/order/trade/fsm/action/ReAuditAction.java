package com.wanmi.sbc.order.trade.fsm.action;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.enums.AuditState;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
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
 * <p>订单回审Action</p>
 * Created by of628-wenzhi on 2017-06-02-下午6:01.
 */

@Component
public class ReAuditAction extends TradeAction {

    @Override
    protected void evaluateInternal(Trade trade, StateRequest request, TradeStateContext tsc) {
        TradeState tradeState = trade.getTradeState();
        Operator operator = tsc.getOperator();
        tradeState.setAuditState(AuditState.NON_CHECKED);
        tradeState.setFlowState(FlowState.INIT);
        tradeState.setPayState(PayState.NOT_PAID);
        String detail = String.format("订单[%s]回审,操作人:%s", trade.getId(), operator.getName());
        trade.appendTradeEventLog(TradeEventLog
                .builder()
                .operator(operator)
                .eventTime(LocalDateTime.now())
                .eventType("订单回审")
                .eventDetail(detail)
                .build());
        save(trade);


        List<ProviderTrade> providerTradeList = providerTradeService.findListByParentId(trade.getId());
        for (ProviderTrade providerTrade : providerTradeList){
            if(providerTrade.getTradeState()!=null){
                providerTrade.getTradeState().setAuditState(AuditState.NON_CHECKED);
                providerTrade.getTradeState().setFlowState(FlowState.INIT);
                providerTrade.getTradeState().setPayState(PayState.NOT_PAID);
                saveProviderTrade(providerTrade);
            }
        }

        super.operationLogMq.convertAndSend(operator, "订单回审", detail);
    }

}
