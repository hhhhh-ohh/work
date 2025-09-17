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
 * @className AccountFundsModifyIsDistributorService
 * @description 新增分销员，更新会员资金-是否分销员字段
 * @date 2021/8/13 4:16 下午
 */
@Slf4j
@Service
public class ModifyAccountFundsIsDistributorService {

    @Autowired private AccountMqConsumerProvider accountMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqModifyAccountFundsIsDistributorService() {
        return message->{
            String json = message.getPayload();
            AccountMqConsumerRequest accountMqConsumerRequest = new AccountMqConsumerRequest();
            accountMqConsumerRequest.setMqContentJson(json);
            accountMqConsumerProvider.modifyAccountFundsIsDistributor(accountMqConsumerRequest);
        };
    }
}
