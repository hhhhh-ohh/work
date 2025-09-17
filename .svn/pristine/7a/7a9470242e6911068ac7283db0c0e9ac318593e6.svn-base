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
 * @className EsPointsGoodsModifyAddedService
 * @description 积分商品-修改上下架同步es
 * @date 2021/8/11 4:25 下午
 */
@Slf4j
@Service
public class EsPointsGoodsModifyAddedService {

    @Autowired private EsMqConsumerProvider esMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqEsPointsGoodsModifyAddedService() {
        return message->{
            String json = message.getPayload();
            EsMqConsumerRequest esMqConsumerRequest = new EsMqConsumerRequest();
            esMqConsumerRequest.setMqContentJson(json);
            esMqConsumerProvider.pointsGoodsModifyAdded(esMqConsumerRequest);
        };
    }
}
