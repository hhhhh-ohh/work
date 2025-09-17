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
 * @description 商家消息发送处理
 * @author malianfeng
 * @date 2022/7/13 11:51
 */
@Slf4j
@Service
public class StoreMessageService {

    @Autowired private MessageMqConsumerProvider messageMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqStoreMessageService() {
        return message->{
            String json = message.getPayload();
            log.info("=============== storeMessageSendConsumer 处理start ===============");
            try {
                MessageMqConsumerRequest messageMqConsumerRequest = new MessageMqConsumerRequest();
                messageMqConsumerRequest.setMqContentJson(json);
                messageMqConsumerProvider.storeMessageSend(messageMqConsumerRequest);
            } catch (Exception e) {
                log.error("商家消息发送失败，data: {}", json, e);
            }
            log.info("=============== storeMessageSendConsumer 处理end ===============");
        };
    }
}
