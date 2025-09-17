package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.empower.api.provider.pay.Lakala.LakalaShareProfitProvider;
import com.wanmi.sbc.empower.api.request.settlement.SettlementRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author edz
 * @className LakalaShareProfitService
 * @description 分账
 * @date 2022/7/20 14:12
 **/
@Slf4j
@Service
public class LakalaShareProfitService {

    @Autowired
    LakalaShareProfitProvider lakalaShareProfitProvider;

    @Bean
    public Consumer<Message<String>> mqLakalaShareProfitService() {
        return message->{
            String json = message.getPayload();
            log.info("MQ拉卡拉分账:{}", json);
            if (json != null){
                SettlementRequest settlementRequest = JSON.parseObject(json, SettlementRequest.class);
                lakalaShareProfitProvider.seporcancel(settlementRequest);
            }
        };
    }
}
