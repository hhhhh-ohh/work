package com.wanmi.sbc.order.orderperformance.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.order.api.request.distribution.ReturnOrderSendMQRequest;
import com.wanmi.sbc.order.api.request.orderperformance.OrderPerformanceSendMQRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 退单状态变更生产者
 * @Autho qiaokang
 * @Date：2019-03-06 16:37:14
 */
@Service
public class OrderPerformanceProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;



    /**
     * 快速退款发送MQ消息
     * @param request
     */
    public void sendOrderPerformanceMessage(OrderPerformanceSendMQRequest request) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ORDER_SERVICE_CREATE_ORDER_PERFORMANCE);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }
}
