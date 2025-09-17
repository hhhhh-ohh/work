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
 * @className ModifyAccountFundsIsLeaderService
 * @description 新增团长，更新会员资金-是否团长字段
 * @date 2021/8/13 4:16 下午
 */
@Slf4j
@Service
public class ModifyAccountFundsIsLeaderService {

    @Autowired private AccountMqConsumerProvider accountMqConsumerProvider;

    @Bean
    public Consumer<Message<String>> mqModifyAccountFundsIsLeaderService() {
        return message->{
            String json = message.getPayload();
            AccountMqConsumerRequest accountMqConsumerRequest = new AccountMqConsumerRequest();
            accountMqConsumerRequest.setMqContentJson(json);
            accountMqConsumerProvider.modifyAccountFundsIsLeader(accountMqConsumerRequest);
        };
    }
}
