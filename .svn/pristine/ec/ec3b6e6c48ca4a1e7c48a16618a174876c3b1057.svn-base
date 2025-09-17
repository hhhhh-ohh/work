package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.customer.LedgerBindInfoToEsRequest;
import com.wanmi.sbc.elastic.api.provider.ledger.EsLedgerBindInfoProvider;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoBatchSaveRequest;
import com.wanmi.sbc.elastic.bean.dto.ledger.EsLedgerBindInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoAddService
 * @description
 * @date 2022/7/14 5:03 PM
 **/
@Slf4j
@Service
public class EsLedgerBindInfoAddService {

    @Autowired
    private EsLedgerBindInfoProvider esLedgerBindInfoProvider;

    @Bean
    public Consumer<Message<String>> mqEsLedgerBindInfoAddService() {
        return message->{
            String json = message.getPayload();
            LedgerBindInfoToEsRequest request = JSONObject.parseObject(json, LedgerBindInfoToEsRequest.class);
            List<EsLedgerBindInfoDTO> dtos = KsBeanUtil.convert(request.getRelVOList(), EsLedgerBindInfoDTO.class);
            EsLedgerBindInfoBatchSaveRequest saveRequest = EsLedgerBindInfoBatchSaveRequest.builder()
                    .infos(dtos)
                    .build();
            esLedgerBindInfoProvider.batchSave(saveRequest);
        };
    }
}
