package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.order.api.provider.mqconsumer.OrderMqConsumerProvider;
import com.wanmi.sbc.order.api.request.mqconsumer.OrderMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className ReduceRestrictedPurchaseNumService
 * @description 异常订单发送限售信息 ——（取消订单，超时未支付，退货，退款，审批未通过）
 * @date 2021/8/17 2:46 下午
 */
@Slf4j
@Service
public class ReduceRestrictedPurchaseNumService {

    @Autowired private OrderMqConsumerProvider orderMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqReduceRestrictedPurchaseNumService() {
        return message->{
            String json = message.getPayload();
            OrderMqConsumerRequest orderMqConsumerRequest = new OrderMqConsumerRequest();
            orderMqConsumerRequest.setMqContentJson(json);
            orderMqConsumerProvider.reduceRestrictedPurchaseNum(orderMqConsumerRequest);
        };
    }
}
