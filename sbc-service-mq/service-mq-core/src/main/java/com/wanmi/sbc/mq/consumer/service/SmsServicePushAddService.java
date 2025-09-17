package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.message.api.request.pushsend.PushSendAddRequest;
import com.wanmi.sbc.mq.message.PushSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className SmsServicePushAddService
 * @description app push短信发送
 * @date 2021/8/17 4:06 下午
 */
@Slf4j
@Service
public class SmsServicePushAddService {

    @Autowired private PushSendService pushSendService;

    @Bean
    public Consumer<Message<String>> mqSmsServicePushAddService() {
        return message->{
            String json = message.getPayload();
            log.info("=============== crm推送pushMQ处理start ===============");
            PushSendAddRequest request = JSONObject.parseObject(json, PushSendAddRequest.class);
            pushSendService.add(request);
            log.info("=============== crm推送pushMQ处理end ===============");
        };
    }
}
