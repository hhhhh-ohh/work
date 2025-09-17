package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.smssend.SmsSendSaveProvider;
import com.wanmi.sbc.message.api.request.smssend.SmsSendSaveRequest;
import com.wanmi.sbc.message.bean.dto.SmsSendDTO;
import com.wanmi.sbc.message.bean.enums.SendStatus;
import com.wanmi.sbc.mq.message.SmsSendTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className SmsSendDataService
 * @description 新增短信发送
 * @date 2021/8/16 2:41 下午
 */
@Slf4j
@Service
public class SmsSendDataService {

    @Autowired private SmsSendTaskService smsSendTaskService;

    @Autowired private SmsSendSaveProvider smsSendSaveProvider;

    @Bean
    public Consumer<Message<String>> mqSmsSendDataService() {
        return message->{
            String json = message.getPayload();
            System.out.println("消费任务内容：".concat(json));
            SmsSendDTO smsSend = JSONObject.parseObject(json, SmsSendDTO.class);
            try {
                this.smsSendTaskService.send(smsSend);
            } catch (Exception e) {
                log.error("发送任务失败：{}", e.getMessage());
                smsSend.setStatus(SendStatus.FAILED);
                smsSend.setMessage("执行发送任务异常！");
                smsSendSaveProvider.save(KsBeanUtil.convert(smsSend, SmsSendSaveRequest.class));
            }
        };
    }
}
