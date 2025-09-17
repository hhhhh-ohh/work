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
 * @description 商品批量改价MQ
 * @author malianfeng
 * @date 2021/9/9 11:02
 */
@Slf4j
@Service
public class GoodsPriceAdjustService {

    @Autowired private GoodsMqConsumerProvider goodsMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqGoodsPriceAdjustService() {
        return message->{
            String json = message.getPayload();
            GoodsMqConsumerRequest goodsMqConsumerRequest = new GoodsMqConsumerRequest();
            goodsMqConsumerRequest.setMqContentJson(json);
            goodsMqConsumerProvider.goodsPriceAdjust(goodsMqConsumerRequest);
        };
    }
}
