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
 * @author xufeng
 * @className DealOrderPointsIncreaseService
 * @description 下单异步更新积分增长信息
 * @date 2021/11/24 1:42 下午
 */
@Slf4j
@Service
public class DealOrderPointsIncreaseService {
    @Autowired private OrderMqConsumerProvider orderMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqDealOrderPointsIncreaseService() {
        return message->{
            String json = message.getPayload();
            OrderMqConsumerRequest request = new OrderMqConsumerRequest();
            request.setMqContentJson(json);
            orderMqConsumerProvider.dealOrderPointsIncrease(request);
        };
    }
}
