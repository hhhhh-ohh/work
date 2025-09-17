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
 * @className SmsServiceMessageSendService
 * @description 发送消息
 * @date 2021/8/11 3:14 下午
 */
@Slf4j
@Service
public class SmsServiceMessageSendService {

    @Autowired private MessageMqConsumerProvider messageMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqSmsServiceMessageSendService() {
        return message->{
            String json = message.getPayload();
            MessageMqConsumerRequest messageMqConsumerRequest = new MessageMqConsumerRequest();
            messageMqConsumerRequest.setMqContentJson(json);
            messageMqConsumerProvider.smsMessageSend(messageMqConsumerRequest);
        };
    }
}
