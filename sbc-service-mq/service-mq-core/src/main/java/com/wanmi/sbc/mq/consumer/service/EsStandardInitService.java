package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.elastic.api.provider.mqconsumer.EsMqConsumerProvider;
import com.wanmi.sbc.elastic.api.request.mqconsumer.EsMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className EsStandardInitService
 * @description elastic模块-标品库-初始化
 * @date 2021/8/12 1:40 下午
 */
@Slf4j
@Service
public class EsStandardInitService {

    @Autowired private EsMqConsumerProvider esMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqEsStandardInitService() {
        return message->{
            String json = message.getPayload();
            EsMqConsumerRequest esMqConsumerRequest = new EsMqConsumerRequest();
            esMqConsumerRequest.setMqContentJson(json);
            esMqConsumerProvider.esStandardInit(esMqConsumerRequest);
        };
    }
}
