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
 * @className ModifyEmployeeDataService
 * @description 业务员交接
 * @date 2021/8/13 5:20 下午
 */
@Slf4j
@Service
public class ModifyEmployeeDataService {

    @Autowired private OrderMqConsumerProvider orderMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqModifyEmployeeDataService() {
        return message->{
            String json = message.getPayload();
            OrderMqConsumerRequest orderMqConsumerRequest = new OrderMqConsumerRequest();
            orderMqConsumerRequest.setMqContentJson(json);
            orderMqConsumerProvider.modifyEmployeeData(orderMqConsumerRequest);
        };
    }
}
