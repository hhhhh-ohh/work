package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.customer.api.provider.ledger.LakalaProvider;
import com.wanmi.sbc.customer.api.request.ledger.LakalaAccountFunctionRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerFunctionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author xuyunpeng
 * @className LedgerAccountAddService
 * @description
 * @date 2022/7/7 2:31 PM
 **/
@Slf4j
@Service
public class LedgerAccountAddService {
    @Autowired
    private LakalaProvider lakalaProvider;

    @Bean
    public Consumer<Message<String>> mqLedgerAccountAddService() {
        return message->{
            String json = message.getPayload();
            log.info("创建清分账户,业务id:{}", json);
            lakalaProvider.excuteFunction(LakalaAccountFunctionRequest.builder()
                    .businessId(json)
                    .type(LedgerFunctionType.CREATE_ACCOUNT)
                    .build());
        };
    }
}
