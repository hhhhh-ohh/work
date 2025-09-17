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
 * @author xufeng
 * @className SendMiniMsgService
 * @description 发送订阅消息延迟MQ生产者消息通道
 * @date 2022/8/16 10:12 上午
 */
@Slf4j
@Service
public class SendMiniMsgService {
    @Autowired
    private MessageMqConsumerProvider messageMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqSendMiniMsgService() {
        return message->{
            String json = message.getPayload();
            log.info("发送订阅消息mq执行开始...");
            MessageMqConsumerRequest messageMqConsumerRequest = new MessageMqConsumerRequest();
            messageMqConsumerRequest.setMqContentJson(json);
            messageMqConsumerProvider.sendMiniProgramSubscribeMessage(messageMqConsumerRequest);
            log.info("发送订阅消息mq执行结束...");
        };
    }
}
