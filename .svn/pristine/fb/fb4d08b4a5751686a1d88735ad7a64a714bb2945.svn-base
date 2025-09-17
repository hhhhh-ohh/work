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
 * @author zgl
 * @className BatchGoodsStockUpdateService
 * @description 批量导入-商品-库存修改
 * @date 2023-03-15 15:00:00
 */
@Slf4j
@Service
public class BatchGoodsStockUpdateService {

    @Autowired private GoodsMqConsumerProvider goodsMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqBatchGoodsStockUpdateService() {
        return message->{
            String json = message.getPayload();
            GoodsMqConsumerRequest goodsMqConsumerRequest = new GoodsMqConsumerRequest();
            goodsMqConsumerRequest.setMqContentJson(json);
            goodsMqConsumerProvider.batchGoodsStockUpdate(goodsMqConsumerRequest);
        };
    }
}
