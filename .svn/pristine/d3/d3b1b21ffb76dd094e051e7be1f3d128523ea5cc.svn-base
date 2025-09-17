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
 * @className FlowMarketingSyncService
 * @description 营销埋点流量信息同步
 * @date 2021/8/11 2:07 下午
 */
@Slf4j
@Service
public class FlowMarketingSyncService {

    @Autowired private AresMqConsumerProvider mqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqFlowMarketingSyncService() {
        return message->{
            String json = message.getPayload();
            MqConsumerRequest mqConsumerRequest = new MqConsumerRequest();
            mqConsumerRequest.setMqContentJson(json);
            mqConsumerProvider.flowMarketingSync(mqConsumerRequest);
        };
    }
}
