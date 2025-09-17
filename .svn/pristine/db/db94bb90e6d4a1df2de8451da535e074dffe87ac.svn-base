package com.wanmi.sbc.order.provider.impl.mqconsumer;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.mqconsumer.OrderMqConsumerProvider;
import com.wanmi.sbc.order.api.request.areas.OrderListAddRequest;
import com.wanmi.sbc.order.api.request.growthvalue.OrderGrowthValueTempQueryRequest;
import com.wanmi.sbc.order.api.request.mqconsumer.OrderMqConsumerRequest;
import com.wanmi.sbc.order.api.request.trade.TradeBackRestrictedRequest;
import com.wanmi.sbc.order.mqconsumer.OrderMqConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className OrderMqConsumerController
 * @description mq消费接口实现
 * @date 2021/8/13 5:27 下午
 **/
@RestController
public class OrderMqConsumerController implements OrderMqConsumerProvider {

    @Autowired
    private OrderMqConsumerService orderMqConsumerService;

    @Override
    public BaseResponse modifyEmployeeData(@RequestBody @Valid OrderMqConsumerRequest request) {
        orderMqConsumerService.modifyEmployeeData(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse cancelOrder(@Valid OrderMqConsumerRequest request) {
        orderMqConsumerService.cancelOrder(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse openGroupon(@Valid OrderMqConsumerRequest request) {
        orderMqConsumerService.openGroupon(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse grouponOrderPaySuccessAutoRefund(@Valid OrderMqConsumerRequest request) {
        orderMqConsumerService.grouponOrderPaySuccessAutoRefund(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse grouponNumLimit(@Valid OrderMqConsumerRequest request) {
        orderMqConsumerService.grouponNumLimit(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse reduceRestrictedPurchaseNum(@Valid OrderMqConsumerRequest request) {
        TradeBackRestrictedRequest restrictedRequest = JSONObject.parseObject(request.getMqContentJson(),TradeBackRestrictedRequest.class);
        orderMqConsumerService.reduceRestrictedPurchaseNum(restrictedRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse dealOrderPointsIncrease(@Valid OrderMqConsumerRequest request){
        orderMqConsumerService.dealOrderPointsIncrease(request);
        return BaseResponse.SUCCESSFUL();
    }
}
