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
 * @className EsGoodsModifyStoreNameByStoreIdService
 * @description 商品-更新店铺信息时，更新商品goods中对应店铺名称，同步es
 * @date 2022/11/26 4:50 下午
 */
@Slf4j
@Service
public class EsGoodsModifyStoreNameByStoreIdService {

    @Autowired private GoodsMqConsumerProvider goodsMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqEsGoodsModifyStoreNameByStoreIdService() {
        return message->{
            String json = message.getPayload();
            GoodsMqConsumerRequest goodsMqConsumerRequest = new GoodsMqConsumerRequest();
            goodsMqConsumerRequest.setMqContentJson(json);
            goodsMqConsumerProvider.goodsStoreNameModify(goodsMqConsumerRequest);
        };
    }
}
