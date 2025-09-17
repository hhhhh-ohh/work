package com.wanmi.sbc.mq.consumer.service;

import com.wanmi.sbc.setting.api.provider.recommend.RecommendProvider;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByPageCodeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author xufeng
 * @className RecommendSyncService
 * @description 种草统计延迟MQ消费者消息通道
 * @date 2022/5/18 15:12 上午
 */
@Slf4j
@Service
public class RecommendSyncService {

    @Autowired
    private RecommendProvider recommendProvider;

    @Bean
    public Consumer<Message<String>> mqRecommendSyncService() {
        return message->{
            String json = message.getPayload();
            log.info("种草统计mq执行开始...");
            recommendProvider.recommendSync(RecommendByPageCodeRequest.builder().pageCode(json).build());
            log.info("种草统计mq执行结束...");
        };
    }
}
