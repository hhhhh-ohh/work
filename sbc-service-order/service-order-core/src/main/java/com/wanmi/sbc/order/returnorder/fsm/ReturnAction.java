package com.wanmi.sbc.order.returnorder.fsm;

import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.common.OperationLogMq;
import com.wanmi.sbc.order.returnorder.fsm.event.ReturnEvent;
import com.wanmi.sbc.order.returnorder.fsm.params.ReturnStateRequest;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 *
 * Created by jinwei on 2017/4/21.
 */
public abstract class ReturnAction implements Action<ReturnFlowState, ReturnEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ReturnOrderService returnOrderService;

    @Autowired
    public OperationLogMq operationLogMq;

    @Override
    public void execute(StateContext<ReturnFlowState, ReturnEvent> stateContext) {
        ReturnStateContext rsc = new ReturnStateContext(stateContext);
        try {
            evaluateInternal(rsc.findReturnOrder(),rsc.findRequest(), rsc);
        } catch (Exception e) {
            rsc.put(Exception.class, e);
            logger.error("[退货]处理, 从状态:{}, 经过事件:{}, 到状态:{}, 出现异常:{}", stateContext.getSource().getId(), stateContext.getTarget().getId(), stateContext.getEvent(), e.getMessage());
        }
    }
    protected abstract void evaluateInternal(ReturnOrder returnOrder, ReturnStateRequest request, ReturnStateContext rsc);
}
