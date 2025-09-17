package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.marketing.api.provider.mqconsumer.MarketingMqConsumerProvider;
import com.wanmi.sbc.marketing.api.request.mqconsumer.MarketingMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className levelRightsIssueCouponsService
 * @description 发放优惠券
 * @date 2021/8/16 11:31 上午
 */
@Slf4j
@Service
public class LevelRightsIssueCouponsService {

    @Autowired private MarketingMqConsumerProvider marketingMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqLevelRightsIssueCouponsService() {
        return message->{
            String json = message.getPayload();
            MarketingMqConsumerRequest marketingMqConsumerRequest = new MarketingMqConsumerRequest();
            marketingMqConsumerRequest.setMqContentJson(json);
            marketingMqConsumerProvider.levelRightsIssueCoupons(marketingMqConsumerRequest);
        };
    }
}
