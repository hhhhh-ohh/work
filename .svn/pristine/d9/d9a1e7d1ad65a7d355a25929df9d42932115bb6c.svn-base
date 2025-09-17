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
 * @author zgl
 * @className BatchCustomerPointsUpdateService
 * @description 批量导入-客户-积分修改
 * @date 2023-03-15 15:00:00
 */
@Slf4j
@Service
public class BatchCustomerPointsUpdateService {

    @Autowired private CustomerMqConsumerProvider customerMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqBatchCustomerPointsUpdateService() {
        return message->{
            String json = message.getPayload();
            CustomerMqConsumerRequest customerMqConsumerRequest = new CustomerMqConsumerRequest();
            customerMqConsumerRequest.setMqContentJson(json);
            customerMqConsumerProvider.batchCustomerPointsUpdate(customerMqConsumerRequest);
        };
    }

}
