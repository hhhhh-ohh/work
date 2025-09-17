package com.wanmi.sbc.mq.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author zhanggaolei
 * @className TestDelayService
 * @description TODO
 * @date 2021/8/5 4:29 下午
 */
@Slf4j
@Service
public class TestDelayService{

    @Bean
    public Consumer<Message<String>> mqTestDelayService() {
        return message->{
            String json = message.getPayload();
            log.info("mq test json:{}", json);
        };
    }
}
