package com.wanmi.sbc.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.message.bean.vo.PushSendVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: sbc-micro-service
 * @create: 2020-02-04 17:38
 **/
@Service
public class PushQueryProducer {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 运营计划push查询详情
     * @param pushSendVO
     */
    public void query(PushSendVO pushSendVO){
        MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
        mqSendDelayDTO.setTopic(ProducerTopic.SMS_PUSH_QUERY);
        mqSendDelayDTO.setData(JSONObject.toJSONString(pushSendVO));
        mqSendDelayDTO.setDelayTime(600000L);
        mqSendProvider.sendDelay(mqSendDelayDTO);
    }
}