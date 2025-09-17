package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.ares.provider.AresMqConsumerProvider;
import com.wanmi.ares.request.mq.MqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className OrderCustomerInfoService
 * @description s2b_statistics统计库记录用户下单信息
 * @date 2021/8/17 2:59 下午
 */
@Slf4j
@Service
public class OrderCustomerInfoService {

    @Autowired private AresMqConsumerProvider aresMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqOrderCustomerInfoService() {
        return message->{
            String json = message.getPayload();
            MqConsumerRequest mqConsumerRequest = new MqConsumerRequest();
            mqConsumerRequest.setMqContentJson(json);
            aresMqConsumerProvider.customerFirstPay(mqConsumerRequest);
        };
    }
}
