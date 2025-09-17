package com.wanmi.sbc.mq.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className OrderOpenGrouponMsgPushToCService
 * @description 开团发送消息推送（C端展示）
 * @date 2021/8/12 10:07 上午
 */
@Slf4j
@Service
public class OrderOpenGrouponMsgPushToCService{

    @Bean
    public Consumer<Message<String>> mqOrderOpenGrouponMsgPushToCService() {
        return message->{
            String json = message.getPayload();
        };
    }
}
