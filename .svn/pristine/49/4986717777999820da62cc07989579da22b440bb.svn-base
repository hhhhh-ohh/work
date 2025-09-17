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
 * @className EsUpdateFlowStateOrderInvoiceService
 * @description 同步订单状态到开票的es数据中
 * @date 2021/8/12 4:51 下午
 */
@Slf4j
@Service
public class EsUpdateFlowStateOrderInvoiceService {

    @Autowired private EsMqConsumerProvider esMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqEsUpdateFlowStateOrderInvoiceService() {
        return message->{
            String json = message.getPayload();
            EsMqConsumerRequest esMqConsumerRequest = new EsMqConsumerRequest();
            esMqConsumerRequest.setMqContentJson(json);
            esMqConsumerProvider.updateFlowStateOrderInvoice(esMqConsumerRequest);
        };
    }
}
