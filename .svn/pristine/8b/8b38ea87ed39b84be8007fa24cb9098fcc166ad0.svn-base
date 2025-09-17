package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.elastic.api.provider.ledger.EsLedgerBindInfoProvider;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoUpdateService
 * @description
 * @date 2022/9/13 3:24 PM
 **/
@Slf4j
@Service
public class EsLedgerBindInfoUpdateService {

    @Autowired
    private EsLedgerBindInfoProvider esLedgerBindInfoProvider;

    @Bean
    public Consumer<Message<String>> mqEsLedgerBindInfoUpdateService() {
        return message->{
            String json = message.getPayload();
            EsLedgerBindInfoUpdateRequest request = JSONObject.parseObject(json, EsLedgerBindInfoUpdateRequest.class);
            esLedgerBindInfoProvider.updateNameAndAccount(request);
        };
    }
}
