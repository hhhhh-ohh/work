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
 * @className DrawCashModifyCustomerNameService
 * @description 会员账户信息修改,触发账户模块-会员提现管理的会员名称修改
 * @date 2021/8/13 4:32 下午
 */
@Slf4j
@Service
public class ModifyDrawCashCustomerNameService {

    @Autowired private AccountMqConsumerProvider accountMqConsumerProvider;


    @Bean
    public Consumer<Message<String>> mqModifyDrawCashCustomerNameService() {
        return message->{
            String json = message.getPayload();
            AccountMqConsumerRequest accountMqConsumerRequest = new AccountMqConsumerRequest();
            accountMqConsumerRequest.setMqContentJson(json);
            accountMqConsumerProvider.modifyAccountDrawCashCustomerName(accountMqConsumerRequest);
        };
    }
}
