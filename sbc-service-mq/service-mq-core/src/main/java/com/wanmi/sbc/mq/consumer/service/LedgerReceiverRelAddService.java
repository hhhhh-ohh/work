package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverBatchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author xuyunpeng
 * @className LedgerReceiverRelAddService
 * @description
 * @date 2022/7/14 2:55 PM
 **/
@Slf4j
@Service
public class LedgerReceiverRelAddService {

    @Autowired
    private LedgerReceiverRelProvider ledgerReceiverRelProvider;

    @Bean
    public Consumer<Message<String>> mqLedgerReceiverRelAddService() {
        return message->{
            String json = message.getPayload();
            log.info("批量新增分账绑定关系，清分账户id:{}", json);
            ledgerReceiverRelProvider.batchAddByAccountId(LedgerReceiverBatchRequest.builder().accountId(json).build());
        };
    }
}
