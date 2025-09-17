package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.elastic.api.provider.groupon.EsGrouponActivityProvider;
import com.wanmi.sbc.elastic.api.request.groupon.EsGrouponActivityInitRequest;
import com.wanmi.sbc.marketing.api.provider.mqconsumer.MarketingMqConsumerProvider;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityModifyStatisticsNumByIdRequest;
import com.wanmi.sbc.marketing.api.request.mqconsumer.MarketingMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className GrouponModifyStatisticsNumService
 * @description 根据不同拼团状态更新不同的统计数据（已成团、待成团、团失败人数）
 * @date 2021/8/17 1:46 下午
 */
@Slf4j
@Service
public class GrouponStatisticsNumService {

    @Autowired private MarketingMqConsumerProvider marketingMqConsumerProvider;

    @Autowired
    private EsGrouponActivityProvider esGrouponActivityProvider;

    @Bean
    public Consumer<Message<String>> mqGrouponStatisticsNumService() {
        return message->{
            String json = message.getPayload();
            MarketingMqConsumerRequest marketingMqConsumerRequest = new MarketingMqConsumerRequest();
            marketingMqConsumerRequest.setMqContentJson(json);
            marketingMqConsumerProvider.grouponStatisticsNum(marketingMqConsumerRequest);

            //更新es
            GrouponActivityModifyStatisticsNumByIdRequest request = JSONObject.parseObject(json, GrouponActivityModifyStatisticsNumByIdRequest.class);
            esGrouponActivityProvider.init(EsGrouponActivityInitRequest.builder().idList(Lists.newArrayList(request.getGrouponActivityId())).build());
        };
    }
}
