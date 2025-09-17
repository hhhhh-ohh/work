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
 * @className EsCustomerLevelDetailAddService
 * @description 新增会员等級，同步ES
 * @date 2021/8/12 4:16 下午
 */
@Slf4j
@Service
public class EsCustomerLevelDetailAddService {

    @Autowired private EsMqConsumerProvider esMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqEsCustomerLevelDetailAddService() {
        return message->{
            String json = message.getPayload();
            EsMqConsumerRequest esMqConsumerRequest = new EsMqConsumerRequest();
            esMqConsumerRequest.setMqContentJson(json);
            esMqConsumerProvider.customerLevelDetailAdd(esMqConsumerRequest);
        };
    }
}
