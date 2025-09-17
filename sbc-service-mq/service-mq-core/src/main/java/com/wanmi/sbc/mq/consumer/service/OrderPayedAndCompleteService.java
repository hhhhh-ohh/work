package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.mq.order.OrderConsumerService;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.seata.spring.annotation.GlobalTransactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className OrderPayedAndCompleteService
 * @description 发送订单支付、订单完成MQ消息
 * @date 2021/8/16 8:23 下午
 */
@Slf4j
@Service
public class OrderPayedAndCompleteService {

    @Autowired private OrderConsumerService orderConsumerService;

    @Bean
    public Consumer<Message<String>> mqOrderPayedAndCompleteService() {
        return this::extracted;
    }

    @SneakyThrows
    @GlobalTransactional
    private void extracted(Message<String> message) {
        String json = message.getPayload();
        try {
            log.info("=============== 订单支付&订单完成MQ处理start ===============");
            TradeVO tradeVO = JSONObject.parseObject(json, TradeVO.class);
//            排除卡券订单
//            if(Objects.isNull(tradeVO.getOrderTag()) || Boolean.FALSE.equals(tradeVO.getOrderTag().getElectronicCouponFlag())) {
                orderConsumerService.doOrderPayed(json);
//            }
            orderConsumerService.doOrderComplete(tradeVO.getId());
            log.info("=============== 订单支付&订单完成MQ处理end ===============");
        } catch (Exception e) {
            log.error("订单支付&订单完成MQ处理异常! param={}", json, e);
            throw e;
        }
    }
}
