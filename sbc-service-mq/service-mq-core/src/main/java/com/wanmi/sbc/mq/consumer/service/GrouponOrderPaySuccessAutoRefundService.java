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
 * @className GrouponOrderPaySuccessAutoRefundService
 * @description 拼团订单-支付成功，订单异常，自动退款
 * @date 2021/8/17 2:18 下午
 */
@Slf4j
@Service
public class GrouponOrderPaySuccessAutoRefundService {

    @Autowired private OrderMqConsumerProvider orderMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqGrouponOrderPaySuccessAutoRefundService() {
        return message->{
            String json = message.getPayload();
            log.info("sendGrouponOrderAutoRefund end");
            OrderMqConsumerRequest orderMqConsumerRequest = new OrderMqConsumerRequest();
            orderMqConsumerRequest.setMqContentJson(json);
            orderMqConsumerProvider.grouponOrderPaySuccessAutoRefund(orderMqConsumerRequest);
        };
    }
}
