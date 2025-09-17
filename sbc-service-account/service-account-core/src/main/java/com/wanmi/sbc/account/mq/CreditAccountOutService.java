package com.wanmi.sbc.account.mq;

import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/***
 * 授信相关消息发送Service
 * @author zhengyang
 * @since 2021/3/12 18:00
 */
@Component
@Service
public class CreditAccountOutService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /***
     * 发送一条授信事件消息
     * @param msgJson
     */
    public void sendCreditStateChangeEvent(String msgJson) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.CREDIT_STATE_CHANGE_EVENT);
        mqSendDTO.setData(msgJson);
        mqSendProvider.send(mqSendDTO);
    }
}
