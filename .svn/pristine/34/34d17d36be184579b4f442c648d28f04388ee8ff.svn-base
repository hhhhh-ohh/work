package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.customer.api.provider.mqconsumer.CustomerMqConsumerProvider;
import com.wanmi.sbc.customer.api.request.mqconsumer.CustomerMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className IncreaseCustomerGrowthValueService
 * @description 增加成长值MQ
 * @date 2021/8/16 10:49 上午
 */
@Slf4j
@Service
public class IncreaseCustomerGrowthValueService {

    @Autowired private CustomerMqConsumerProvider customerMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqIncreaseCustomerGrowthValueService() {
        return message->{
            String json = message.getPayload();
            CustomerMqConsumerRequest customerMqConsumerRequest = new CustomerMqConsumerRequest();
            customerMqConsumerRequest.setMqContentJson(json);
            customerMqConsumerProvider.increaseCustomerGrowthValue(customerMqConsumerRequest);
        };
    }
}
