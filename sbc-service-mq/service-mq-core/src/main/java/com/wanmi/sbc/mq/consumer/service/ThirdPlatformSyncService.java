package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeProvider;
import com.wanmi.sbc.order.api.request.thirdplatformtrade.ThirdPlatformTradeAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className ThirdPlatformSyncService
 * @description 第三方平台订单-支付成功，订单下单同步
 * @date 2021/8/17 9:21 上午
 */
@Slf4j
@Service
public class ThirdPlatformSyncService {

    @Autowired private ThirdPlatformTradeProvider thirdPlatformTradeProvider;

    @Bean
    public Consumer<Message<String>> mqThirdPlatformSyncService() {
        return message->{
            String json = message.getPayload();
            try {
                log.info("=============== 订单同步至第三方平台MQ处理start ===============");
                thirdPlatformTradeProvider.add(
                        ThirdPlatformTradeAddRequest.builder().businessId(json).build());
                log.info("=============== 订单同步至第三方平台MQ处理end ===============");
            } catch (Exception e) {
                log.error("订单同步至第三方平台MQ处理异常! param={}", json, e);
            }
        };
    }
}
