package com.wanmi.sbc.order.trade.fsm.params;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.trade.fsm.event.TradeEvent;
import com.wanmi.sbc.common.base.Operator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 状态扭转 请求参数
 * Created by jinwei on 29/3/2017.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateRequest extends BaseRequest {

    /**
     * 订单编号
     */
    private String tid;

    /**
     * 操作人
     */
    private Operator operator;

    /**
     * 事件操作
     */
    private TradeEvent event;

    /**
     *
     */
    private Object data;

    /***
     * 是否需要退积分
     */
    private Boolean needRefundPoints;

    /**
     * 周期购提前几天提醒发货
     */
    private Integer remindShipping;
}
