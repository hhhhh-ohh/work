package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.order.api.provider.orderperformance.OrderPerformanceQueryProvider;
import com.wanmi.sbc.order.api.request.orderperformance.OrderPerformanceModifyRequest;
import com.wanmi.sbc.order.api.request.orderperformance.OrderPerformanceSendMQRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * 订单服务-创建订单业绩MQ处理
 */
@Slf4j
@Service
public class OrderServiceCreateOrderPerformanceService {

    /**
     * 订单服务-创建订单业绩MQ处理
     */
    @Autowired
    private OrderPerformanceQueryProvider orderPerformanceQueryProvider ;


    @Bean
    public Consumer<Message<String>> mqOrderServiceCreateOrderPerformanceService() {
        return this::extracted;
    }

    public void extracted(Message<String> message) {
        String json = message.getPayload();
        try {
            log.info("=============== 唯一码业绩计算处理start ===============");
            OrderPerformanceSendMQRequest orderPerformanceSendMQRequest =
                    JSONObject.parseObject(json, OrderPerformanceSendMQRequest.class);

            // 订单号
            String orderId = orderPerformanceSendMQRequest.getOrderId();

            OrderPerformanceModifyRequest orderPerformanceModifyRequest = new OrderPerformanceModifyRequest();
            orderPerformanceModifyRequest.setId(orderId);


            log.info("开始唯一码业绩计算处理: {}", JSONObject.toJSONString(orderPerformanceModifyRequest));
            orderPerformanceQueryProvider.createOrderPerformance(orderPerformanceModifyRequest);
            log.info("结束唯一码业绩计算处理: {}", JSONObject.toJSONString(orderPerformanceModifyRequest));

            log.info("=============== 唯一码业绩计算处理end ===============");
        } catch (Exception e) {
            log.error("唯一码业绩计算处理MQ处理异常! param={}", json, e);
        }
    }

}
