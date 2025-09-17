package com.wanmi.sbc.mq.consumer.service;


import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.goodsfootmark.GoodsFootmarkSaveProvider;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * 新增我的足迹
 */
@Service
public class GoodsFootmarkService {

    @Autowired
    private GoodsFootmarkSaveProvider goodsFootmarkSaveProvider;

    @Autowired
    private GeneratorService generatorService;

    @Bean
    public Consumer<Message<String>> mqGoodsFootmarkService() {
        return message->{
            String json = message.getPayload();
            GoodsFootmarkAddRequest goodsFootmarkAddRequest = JSON.parseObject(json, GoodsFootmarkAddRequest.class);
            goodsFootmarkAddRequest.setCreateTime(LocalDateTime.now());
            goodsFootmarkAddRequest.setDelFlag(DeleteFlag.NO);
            goodsFootmarkAddRequest.setFootmarkId(generatorService.generateNextId());
            goodsFootmarkSaveProvider.add(goodsFootmarkAddRequest);
        };
    }
}
