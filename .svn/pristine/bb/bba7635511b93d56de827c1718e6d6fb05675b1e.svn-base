package com.wanmi.sbc.account.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 10分鐘後，定時取消在綫還款未還款的還款單
 * @Autho chenli
 * @Date：2021-03-05 17:47:18
 */
@Service
public class CreditProducerMqService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 定时取消还款记录
     * @param repayId
     * @param customerId
     * @param millis
     * @return
     */
    public void sendMQForRepayCancel(String repayId, String customerId, Long millis) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",repayId);
        jsonObject.put("customerId",customerId);
        MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
        mqSendDelayDTO.setTopic(ProducerTopic.CUSTOMER_CREDIT_REPAY_CANCEL);
        mqSendDelayDTO.setData(jsonObject.toJSONString());
        mqSendDelayDTO.setDelayTime(millis);
        mqSendProvider.sendDelay(mqSendDelayDTO);
    }
}
