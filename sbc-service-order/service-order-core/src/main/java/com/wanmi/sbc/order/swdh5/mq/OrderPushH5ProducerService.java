package com.wanmi.sbc.order.swdh5.mq;

import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 创单或者退单 推送h5 状态变更生产者
 * @Autho lfx
 * @Date：2025/5/9 14:05
 */
@Service
@Slf4j
public class OrderPushH5ProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;
    /**
     * 推送订单到h5 发送mq消息
     * @param tradeId 订单列表
     * @return
     */
    public void sendTradeToH5CreateOrderMessage(String tradeId){
        try {
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.TRADE_TO_H5_CREATE_ORDER);
            mqSendDTO.setData(tradeId);
            mqSendProvider.send(mqSendDTO);
        }catch (Exception e){
            log.error("支付回调推送订单到h5 发送MQ异常", e);
        }
    }
    /**
     * 推送退单到h5 状态变更
     * @param refundId 退单id
     * @return
     */
    public void sendRefundOrderToH5Message(String refundId){
        try {
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.REFUND_ORDER_TO_H5);
            mqSendDTO.setData(refundId);
            mqSendProvider.send(mqSendDTO);
        }catch (Exception e){
            log.error("退单回调推送退单到h5状态变更 发送MQ异常", e);
        }
    }
}
