package com.wanmi.sbc.mq.delay.service;

import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.constant.MqConstant;
import com.wanmi.sbc.mq.constant.RedisConstant;
import com.wanmi.sbc.mq.delay.bean.MqDelay;
import com.wanmi.sbc.mq.delay.dao.MqDelayRepository;
import com.wanmi.sbc.mq.producer.CommonProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author zhanggaolei
 * @className DelayJobService
 * @description TODO
 * @date 2021/9/23 6:08 下午
 */
@Slf4j
@Service
public class DelayJobService {

    @Autowired private MqDelayRepository repository;

    @Autowired private RedisTemplate<String, String> redisTemplate;

    @Autowired private DelayService delayService;

    @Autowired private CommonProducerService producerService;

    public void process() {
        if (delayService.getFlushType().isRedis()) {
            cacheProcess();
        } else {
            dbProcess();
        }
    }

    private void dbProcess() {
        // 取当前时间往前推180秒，防止已经存在时间轮盘的中的数据未发送
        LocalDateTime dateTime = LocalDateTime.now().minusSeconds(180);
        PageRequest pageRequest = PageRequest.of(0, MqConstant.PAGE_SIZE, Sort.by(Sort.Direction.ASC, "exprTime"));
        for (; ; ) {
            List<MqDelay> list =
                    repository.findAllByExprTimeBeforeAndStatus(dateTime, 0, pageRequest);
            if (CollectionUtils.isNotEmpty(list)) {
                for (MqDelay mqDelay : list) {

                    MqSendDTO sendDTO = new MqSendDTO();
                    sendDTO.setTopic(mqDelay.getTopic());
                    sendDTO.setData(mqDelay.getData());
                    producerService.send(sendDTO);

                    mqDelay.setStatus(1);
                    mqDelay.setSendTime(LocalDateTime.now());
                    repository.save(mqDelay);
                    log.info("延迟队列发送成功：{}", mqDelay);
                }
            } else {
                return;
            }
        }
    }

    private void cacheProcess() {
        // 取当前时间往前推180秒，防止已经存在时间轮盘的中的数据未发送
        Long score = System.currentTimeMillis() - 180*1000;
        Set<String> zsetKeys = redisTemplate.keys(RedisConstant.DELAY_ZSET+"*");
        if (CollectionUtils.isNotEmpty(zsetKeys)) {
            for (String zsetKey : zsetKeys) {
                for (; ; ) {
                    Set<ZSetOperations.TypedTuple<String>> result =
                            redisTemplate
                                    .opsForZSet()
                                    .rangeByScoreWithScores(
                                            zsetKey, -1, score, 0, MqConstant.PAGE_SIZE);
                    if (result == null || result.size() <= 0) {
                        break;
                    } else {
                        for (ZSetOperations.TypedTuple<String> zsetField : result) {
                            String key = RedisConstant.getId(zsetField.getValue());
                            if (StringUtils.isNotBlank(key)) {
                                long id = Long.parseLong(key);
                                delayService.cacheProcess(id,zsetKey);
                            }
                        }
                    }
                }
            }
        }
    }


}