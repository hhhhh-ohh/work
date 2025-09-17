package com.wanmi.sbc.mq.delay.service;

import com.google.common.collect.EvictingQueue;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.constant.MqConstant;
import com.wanmi.sbc.mq.constant.RedisConstant;
import com.wanmi.sbc.mq.delay.DelayProperties;
import com.wanmi.sbc.mq.delay.bean.MqDelay;
import com.wanmi.sbc.mq.delay.dao.MqDelayRepository;
import com.wanmi.sbc.mq.producer.CommonProducerService;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @author zhanggaolei
 * @className StartedLoadMqDelay
 * @description
 * @date 2022/6/2 11:08
 */
@Slf4j
@Order(7)
@Component
public class StartedLoadMqDelayService implements CommandLineRunner {

    @Autowired private DelayProperties delayProperties;

    @Autowired private MqDelayRepository mqDelayRepository;

    @Autowired private DelayService delayService;

    @Autowired private CommonProducerService producerService;

    @Autowired private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    public void init() {
        if (delayProperties.getStartedReload()) {
            switch (delayProperties.getFlushMode()) {
                case MYSQL:
                    dbLoad();
                    break;
                case REDIS:
                    cacheLoad();
                    break;
                default:
                    break;
            }
        }
    }

    private void dbLoad() {
        // 取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        PageRequest pageRequest =
                PageRequest.of(0, MqConstant.PAGE_SIZE, Sort.by(Sort.Direction.ASC, "exprTime"));
        long maxId = 0;
        for (; ; ) {
            List<MqDelay> list =
                    mqDelayRepository.findAllByNoSend(dateTime, MqConstant.IP, maxId, pageRequest);
            if (CollectionUtils.isNotEmpty(list)) {
                for (MqDelay mqDelay : list) {
                    // 如果过期时间小于5s则直接发送
                    if (mqDelay.getExprTime().minusSeconds(5).isBefore(LocalDateTime.now())) {
                        MqSendDTO sendDTO = new MqSendDTO();
                        sendDTO.setTopic(mqDelay.getTopic());
                        sendDTO.setData(mqDelay.getData());
                        producerService.send(sendDTO, mqDelay.getTraceId());

                        mqDelay.setStatus(1);
                        mqDelay.setSendTime(LocalDateTime.now());
                        mqDelayRepository.save(mqDelay);
                        log.info("延迟队列发送成功：{}", mqDelay);
                    } else {
                        // 重新计算过期时间于当前时间的差值，重新放入时间轮
                        delayService.addTimer(
                                mqDelay.getId(),
                                java.time.Duration.between(
                                                LocalDateTime.now(), mqDelay.getExprTime())
                                        .toMillis());
                    }
                    maxId = mqDelay.getId();
                }
            } else {
                return;
            }
        }
    }

    private void cacheLoad() {
        //

        Queue<String> fifo = EvictingQueue.create(MqConstant.PAGE_SIZE);
        long minScore = System.currentTimeMillis();
        long maxScore = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 365;
        long lastScore = 0;
        long offset = 0;
        for (; ; ) {

            Set<ZSetOperations.TypedTuple<String>> result =
                    redisTemplate
                            .opsForZSet()
                            .rangeByScoreWithScores(
                                    RedisConstant.DELAY_ZSET_IP,
                                    minScore,
                                    maxScore,
                                    offset,
                                    MqConstant.PAGE_SIZE);
            if (result == null || result.size() <= 0) {
                break;
            } else {
                offset=1;
                for (ZSetOperations.TypedTuple<String> zsetField : result) {
                    String key = RedisConstant.getId(zsetField.getValue());
                    if (fifo.contains(key)) {
                        offset++;
                    } else {
                        if (StringUtils.isNotBlank(key)) {
                            long id = Long.parseLong(key);
                            if ((zsetField.getScore() - System.currentTimeMillis()) < 5000) {
                                delayService.cacheProcess(id);
                            } else {
                                // 重新计算过期时间于当前时间的差值，重新放入时间轮
                                delayService.addTimer(
                                        id,
                                        zsetField.getScore().longValue()
                                                - System.currentTimeMillis());
                            }

                        } else {
                            return;
                        }
                        minScore = zsetField.getScore().longValue();
                        if(lastScore!=minScore){
                            lastScore = minScore;
                            offset = 1;
                        }

                        fifo.add(key);
                    }
                }
            }
        }
    }
}