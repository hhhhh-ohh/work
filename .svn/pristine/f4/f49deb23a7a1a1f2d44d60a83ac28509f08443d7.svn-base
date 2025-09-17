package com.wanmi.sbc.order.trade.fsm;

import com.wanmi.sbc.order.common.OperationLogMq;
import com.wanmi.sbc.order.message.StoreMessageBizService;
import com.wanmi.sbc.order.trade.fsm.event.TradeEvent;
import com.wanmi.sbc.order.trade.fsm.params.StateRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * Created by Administrator on 2017/4/19.
 */
public abstract class TradeAction implements Action<FlowState, TradeEvent> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected OperationLogMq operationLogMq;

    @Autowired
    protected TradeService tradeService;

    @Autowired
    protected ProviderTradeService providerTradeService;

    @Autowired
    protected StoreMessageBizService storeMessageBizService;


    @Override
    public void execute(StateContext<FlowState, TradeEvent> stateContext) {
        TradeStateContext tsc = new TradeStateContext(stateContext);
        try {
            evaluateInternal(tsc.getTrade(), tsc.getRequest(), tsc);
        } catch (Exception e) {
            tsc.put(Exception.class, e);
            logger.error("处理, 从状态[ {} ], 经过事件[ {} ], 到状态[ {} ], 出现异常：", stateContext.getSource().getId(), stateContext.getEvent(), stateContext.getTarget().getId(), e);
        }
    }

    /**
     * @param trade
     */
    protected void save(Trade trade) {
        tradeService.updateTrade(trade);
    }

    /**
     * @param trade
     */
    protected void saveProviderTrade(ProviderTrade trade) {
        providerTradeService.updateProviderTrade(trade);
    }


    protected abstract void evaluateInternal(Trade trade, StateRequest request, TradeStateContext tsc);
}
