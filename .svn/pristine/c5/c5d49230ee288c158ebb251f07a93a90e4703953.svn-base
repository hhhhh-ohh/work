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
public class TradeToH5CreateOrderService {
    @Autowired
    private SmallProvider smallProvider;

    @Bean
    public Consumer<Message<String>> mqTradeToH5CreateOrderService() {
        return this::extracted;
    }

    public void extracted(Message<String> message) {
        String json = message.getPayload();
        try {
            log.info("=============== 推送订单到h5平台MQ处理start ===============订单号:{}", json);
            BaseResponse h5OrderByTidBaseResponse = smallProvider.createH5OrderByTid(json);
            log.info("=============== 推送订单到h5平台MMQ处理end ===============订单号:{},响应数据:{}", json, h5OrderByTidBaseResponse);
        } catch (Exception e) {
            log.error("推送订单到h5平台MQ处理异常! param={}", json, e);
            // 重新抛出异常，触发RocketMQ重试机制
            throw new RuntimeException("处理H5订单创建失败", e);
        }
    }
}
