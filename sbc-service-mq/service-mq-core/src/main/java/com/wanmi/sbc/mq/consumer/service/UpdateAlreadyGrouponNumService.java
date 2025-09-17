package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.goods.api.provider.mqconsumer.GoodsMqConsumerProvider;
import com.wanmi.sbc.goods.api.request.mqconsumer.GoodsMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className UpdateAlreadyGrouponNum
 * @description 更新已成团人数
 * @date 2021/8/17 2:01 下午
 */
@Slf4j
@Service
public class UpdateAlreadyGrouponNumService {

    @Autowired private GoodsMqConsumerProvider goodsMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqUpdateAlreadyGrouponNumService() {
        return message->{
            String json = message.getPayload();
            GoodsMqConsumerRequest goodsMqConsumerRequest = new GoodsMqConsumerRequest();
            goodsMqConsumerRequest.setMqContentJson(json);
            goodsMqConsumerProvider.updateAlreadyGrouponNum(goodsMqConsumerRequest);
        };
    }
}
