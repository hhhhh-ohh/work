package com.wanmi.sbc.order.returnorder.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.order.api.request.distribution.ReturnOrderSendMQRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 退单状态变更生产者
 * @Autho qiaokang
 * @Date：2019-03-06 16:37:14
 */
@Service
public class ReturnOrderProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 退单状态变更，发送MQ消息：用于分销任务临时表退单数量加减处理
     * @param request
     */
    public void returnOrderFlow(ReturnOrderSendMQRequest request) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ORDER_SERVICE_RETURN_ORDER_FLOW);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

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
     * 快速退款发送MQ消息
     * @param request
     */
    public void sendFastRefundMessage(ReturnOrderSendMQRequest request) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ORDER_SERVICE_FAST_RETURN_ORDER);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }
}
