package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.account.api.provider.mqconsumer.AccountMqConsumerProvider;
import com.wanmi.sbc.account.api.request.mqconsumer.AccountMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className CustomerCreditRepayCancelService
 * @description 定时取消授信还款记录
 * @date 2021/8/16 10:38 上午
 */
@Slf4j
@Service
public class CustomerCreditRepayCancelService {

    @Autowired private AccountMqConsumerProvider accountMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqCustomerCreditRepayCancelService() {
        return message->{
            String json = message.getPayload();
            AccountMqConsumerRequest accountMqConsumerRequest = new AccountMqConsumerRequest();
            accountMqConsumerRequest.setMqContentJson(json);
            accountMqConsumerProvider.customerCreditRepayCancel(accountMqConsumerRequest);
        };
    }
}
