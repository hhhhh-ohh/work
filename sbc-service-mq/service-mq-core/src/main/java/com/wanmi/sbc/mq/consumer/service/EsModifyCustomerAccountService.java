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
 * @className EsCustomerModifyCustomerAccountService
 * @description 修改会员账号成功，发送订单支付MQ消息，同步ES
 * @date 2021/8/12 10:48 上午
 */
@Slf4j
@Service
public class EsModifyCustomerAccountService {

    @Autowired private EsMqConsumerProvider esMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqEsModifyCustomerAccountService() {
        return message->{
            String json = message.getPayload();
            EsMqConsumerRequest esMqConsumerRequest = new EsMqConsumerRequest();
            esMqConsumerRequest.setMqContentJson(json);
            esMqConsumerProvider.modifyCustomerAccount(esMqConsumerRequest);
        };
    }
}
