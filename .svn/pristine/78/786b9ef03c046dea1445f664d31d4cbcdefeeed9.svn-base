package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.request.distribution.ReturnOrderSendMQRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderOnlineRefundByTidRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className OrderServiceReturnOrderFlowService
 * @description 退单状态变更，发送MQ消息
 * @date 2021/8/16 3:00 下午
 */
@Slf4j
@Service
public class OrderServiceFastReturnOrderService {

    /** 注入极速退款任务service */
    @Autowired
    private ReturnOrderProvider returnOrderProvider;


    @Bean
    public Consumer<Message<String>> mqOrderServiceFastReturnOrderService() {
        return this::extracted;
    }

    public void extracted(Message<String> message) {
        String json = message.getPayload();
        try {
            log.info("=============== 极速退款MQ处理start ===============");
            ReturnOrderSendMQRequest returnOrderSendMQRequest =
                    JSONObject.parseObject(json, ReturnOrderSendMQRequest.class);

            String rid = returnOrderSendMQRequest.getReturnId();
            Operator operator = returnOrderSendMQRequest.getOperator();

            ReturnOrderOnlineRefundByTidRequest onlineRefundByTidRequest = ReturnOrderOnlineRefundByTidRequest.builder()
                    .returnOrderCode(rid)
                    .operator(operator)
                    .build();
            log.info("开始处理在线退款: {}", JSONObject.toJSONString(onlineRefundByTidRequest));
            returnOrderProvider.doFastRefund(onlineRefundByTidRequest);
            log.info("结束处理在线退款: {}", JSONObject.toJSONString(onlineRefundByTidRequest));

            log.info("=============== 极速退款MQ处理end ===============");
        } catch (Exception e) {
            log.error("极速退款MQ处理异常! param={}", json, e);
        }
    }

}
