package com.wanmi.sbc.order.pointstrade.fsm;

import com.wanmi.sbc.order.bean.enums.PointsFlowState;
import com.wanmi.sbc.order.common.OperationLogMq;
import com.wanmi.sbc.order.pointstrade.fsm.event.PointsTradeEvent;
import com.wanmi.sbc.order.pointstrade.fsm.params.PointsTradeStateRequest;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * @Author lvzhenwei
 * @Description 积分订单状态机Action
 * @Date 11:05 2019/5/29
 * @Param
 * @return
 **/
public abstract class PointsTradeAction implements Action<PointsFlowState, PointsTradeEvent> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected OperationLogMq operationLogMq;

    @Autowired
    protected TradeService tradeService;

    @Override
    public void execute(StateContext<PointsFlowState, PointsTradeEvent> stateContext) {
        PointsTradeStateContext tsc = new PointsTradeStateContext(stateContext);
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


    protected abstract void evaluateInternal(Trade trade, PointsTradeStateRequest request, PointsTradeStateContext tsc);
}
