package com.wanmi.sbc.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知节点消息发送生产者
 */
@Service
public class MessageSendProducer {

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
}
