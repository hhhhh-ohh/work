package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.message.api.provider.mqconsumer.MessageMqConsumerProvider;
import com.wanmi.sbc.message.api.request.mqconsumer.MessageMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className SmsPushQueryService
 * @description 运营计划push查询详情
 * @date 2021/8/11 6:05 下午
 */
@Slf4j
@Service
public class SmsPushQueryService {

    @Autowired private MessageMqConsumerProvider messageMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqSmsPushQueryService() {
        return message->{
            String json = message.getPayload();
            MessageMqConsumerRequest request = new MessageMqConsumerRequest();
            request.setMqContentJson(json);
            messageMqConsumerProvider.smsPushQuery(request);
        };
    }
}
