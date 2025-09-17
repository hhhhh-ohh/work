package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.empower.api.provider.mqconsumer.EmpowerMqConsumerProvider;
import com.wanmi.sbc.empower.api.request.mqconsumer.EmpowerMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className GoodsSyncNoticeService
 * @description 商品临时表同步完成通知
 * @date 2021/8/18 11:10 上午
 */
@Slf4j
@Service
public class GoodsSyncNoticeService {

    @Autowired private EmpowerMqConsumerProvider empowerMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqGoodsSyncNoticeService() {
        return message->{
            String json = message.getPayload();
            EmpowerMqConsumerRequest empowerMqConsumerRequest = new EmpowerMqConsumerRequest();
            empowerMqConsumerRequest.setMqContentJson(json);
            empowerMqConsumerProvider.goodsTempSyncOver(empowerMqConsumerRequest);
        };
    }
}
