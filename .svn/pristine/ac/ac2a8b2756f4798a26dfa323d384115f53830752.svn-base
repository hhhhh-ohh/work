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
 * @className DealFlashSaleRecordService
 * @description 秒杀订单--异步处理销量和个人购买记录
 * @date 2021/8/13 1:42 下午
 */
@Slf4j
@Service
public class DealFlashSaleRecordService {

    @Autowired private GoodsMqConsumerProvider goodsMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqDealFlashSaleRecordService() {
        return message->{
            String json = message.getPayload();
            GoodsMqConsumerRequest goodsMqConsumerRequest = new GoodsMqConsumerRequest();
            goodsMqConsumerRequest.setMqContentJson(json);
            goodsMqConsumerProvider.dealFlashSaleRecord(goodsMqConsumerRequest);
        };
    }
}
