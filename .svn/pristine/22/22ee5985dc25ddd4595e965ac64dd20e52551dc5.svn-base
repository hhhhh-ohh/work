package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.mq.order.OrderConsumerService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className OrderCompleteSrevice
 * @description 订单完成时，发送订单完成MQ消息
 * @date 2021/8/16 8:32 下午
 */
@Slf4j
@Service
public class OrderCompleteService {

    @Autowired private OrderConsumerService orderConsumerService;

    @Bean
    public Consumer<Message<String>> mqOrderCompleteService() {
        return this::extracted;
    }

    @GlobalTransactional
    private void extracted(Message<String> message) {
        String json = message.getPayload();
        orderConsumerService.doOrderComplete(json);
    }
}
