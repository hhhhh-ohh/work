package com.wanmi.sbc.order.returnorder.fsm.action;

import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.order.returnorder.fsm.ReturnAction;
import com.wanmi.sbc.order.returnorder.fsm.ReturnStateContext;
import com.wanmi.sbc.order.returnorder.fsm.event.ReturnEvent;
import com.wanmi.sbc.order.returnorder.fsm.params.ReturnStateRequest;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.model.value.ReturnEventLog;
import com.wanmi.sbc.common.base.Operator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 取消退单
 * Created by jinwei on 21/4/2017.
 */
@Component
public class CancelReturnAction extends ReturnAction {
    @Override
    protected void evaluateInternal(ReturnOrder returnOrder, ReturnStateRequest request, ReturnStateContext rsc) {
        Operator operator = rsc.findOperator();
        returnOrder.setReturnFlowState(ReturnFlowState.VOID);
        returnOrder.setRejectReason(request.getData().toString());
        ReturnEventLog eventLog = ReturnEventLog.builder()
                .operator(operator)
                .eventTime(LocalDateTime.now())
                .eventType(ReturnEvent.VOID.getDesc())
                .remark(request.getData().toString())
                .build();
        if (Objects.equals(Platform.SUPPLIER,operator.getPlatform())){
            eventLog.setEventDetail(String.format("%s审核驳回了退单,退单号%s", operator.getName(), returnOrder.getId()));
        }else {
            eventLog.setEventDetail(String.format("%s取消了退单,退单号%s", operator.getName(), returnOrder.getId()));
        }
        returnOrder.appendReturnEventLog(eventLog);
        returnOrderService.updateReturnOrder(returnOrder);
        super.operationLogMq.convertAndSend(operator, ReturnEvent.VOID.getDesc(), eventLog.getEventDetail());
    }
}
