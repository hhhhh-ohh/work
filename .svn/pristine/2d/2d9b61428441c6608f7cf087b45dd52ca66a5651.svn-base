package com.wanmi.sbc.account.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageMqService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 发送push、站内信、短信
     * @param request
     */
    public void sendMessage(MessageMQRequest request){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.SMS_SERVICE_MESSAGE_SEND);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 发送商家消息
     * @param request
     */
    public void sendStoreMessage(StoreMessageMQRequest request){
        log.info("商家消息发送开始，request:{}", request);
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.STORE_MESSAGE_SEND);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }
}
