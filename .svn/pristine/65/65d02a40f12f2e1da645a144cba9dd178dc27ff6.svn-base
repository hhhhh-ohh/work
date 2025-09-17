package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.small.SmallProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class TradeRefundToH5Service {
    @Autowired
    private SmallProvider smallProvider;

    @Bean
    public Consumer<Message<String>> mqRefundOrderToH5Service() {
        return this::extracted;
    }

    public void extracted(Message<String> message) {
        String json = message.getPayload();
        try {
            log.info("=============== Refund推送退单到h5平台MQ处理start ===============退单号:{}", json);
            BaseResponse h5OrderByTidBaseResponse = smallProvider.refundH5OrderByRefundId(json);
            log.info("=============== Refund推送退单到h5平台MMQ处理end ===============退单号:{},响应数据:{}", json, h5OrderByTidBaseResponse);
        } catch (Exception e) {
            log.error("Refund推送退单到h5平台MQ处理异常! param={}", json, e.getMessage());
            // 重新抛出异常，触发RocketMQ重试机制
            throw new RuntimeException("Refund处理退单到h5,推送退单失败", e);
        }
    }
}
