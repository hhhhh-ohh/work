package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.ares.provider.AresMqConsumerProvider;
import com.wanmi.ares.request.mq.MqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className FlowCustomerSyncService
 * @description mq消费，同步流量数据
 * @date 2021/8/11 10:58 上午
 */
@Slf4j
@Service
public class FlowCustomerSyncService {

    @Autowired private AresMqConsumerProvider mqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqFlowCustomerSyncService() {
        return message->{
            String json = message.getPayload();
            MqConsumerRequest mqConsumerRequest = new MqConsumerRequest();
            mqConsumerRequest.setMqContentJson(json);
            mqConsumerProvider.flowCustomerSync(mqConsumerRequest);
        };
    }
}
