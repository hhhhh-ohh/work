package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.message.api.provider.mqconsumer.MessageMqConsumerProvider;
import com.wanmi.sbc.message.api.request.mqconsumer.MessageMqConsumerRequest;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @description 商家公告发送处理
 * @author malianfeng
 * @date 2022/7/7 11:51
 */
@Slf4j
@Service
public class StoreNoticeService {

    @Autowired private MessageMqConsumerProvider messageMqConsumerProvider;

    @Autowired private RedissonClient redissonClient;

    private static final String SEND_STORE_NOTICE_LOCK_KEY = "send_store_notice:";

    @Bean
    public Consumer<Message<String>> mqStoreNoticeService() {
        return message->{
            String json = message.getPayload();
            log.info("=============== storeNoticeSendConsumer 处理start ===============");
            Long noticeId = JSON.parseObject(json, Long.class);
            // 分布式锁，防止同一个任务同时执行
            String lockKey = SEND_STORE_NOTICE_LOCK_KEY + noticeId;
            RLock rLock = redissonClient.getFairLock(lockKey);
            rLock.lock();
            try {
                MessageMqConsumerRequest messageMqConsumerRequest = new MessageMqConsumerRequest();
                messageMqConsumerRequest.setMqContentJson(json);
                messageMqConsumerProvider.storeNoticeSend(messageMqConsumerRequest);
            } catch (Exception e) {
                log.error("商家公告任务发送失败，data: {}", json, e);
            } finally {
                rLock.unlock();
                log.info("=============== storeNoticeSendConsumer 处理end ===============");
            }
        };
    }
}
