package com.wanmi.sbc.marketing.mq;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.marketing.coupon.request.CouponSendMiniMsgRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MQ生产者
 * @author: xufeng
 * @createDate: 2022/8/22 13:57
 * @version: 1.0
 */
@Service
@Slf4j
public class ProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 小程序订阅消息推送
     * @param messageRequest 小程序订阅消息入参
     */
    public void sendMiniProgramSubscribeMessage(CouponSendMiniMsgRequest messageRequest){
        MqSendDelayDTO mqSendDTO = new MqSendDelayDTO();
        mqSendDTO.setData(JSON.toJSONString(messageRequest));
        mqSendDTO.setTopic(ProducerTopic.SEND_MINI_PROGRAM_SUBSCRIBE_MSG);
        mqSendDTO.setDelayTime(6000L);
        mqSendProvider.sendDelay(mqSendDTO);
    }
}
