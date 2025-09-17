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
 * @className ModifyFundsCustomerNameAndAccountService
 * @description 会员名称、账号修改,触发账户模块-会员资金的会员名称、账号修改
 * @date 2021/8/13 4:02 下午
 */
@Slf4j
@Service
public class ModifyFundsCustomerNameAndAccountService {

    @Autowired private AccountMqConsumerProvider accountMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqModifyFundsCustomerNameAndAccountService() {
        return message->{
            String json = message.getPayload();
            AccountMqConsumerRequest accountMqConsumerRequest = new AccountMqConsumerRequest();
            accountMqConsumerRequest.setMqContentJson(json);
            accountMqConsumerProvider.modifyFundsCustomerNameAndAccount(accountMqConsumerRequest);
        };
    }
}
