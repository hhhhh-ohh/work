package com.wanmi.sbc.mq.delay.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.wanmi.sbc.mq.constant.MqConstant;
import com.wanmi.sbc.mq.constant.RedisConstant;
import com.wanmi.sbc.mq.delay.DelayProperties;
import com.wanmi.sbc.mq.delay.bean.MqDelay;
import com.wanmi.sbc.mq.delay.core.TimedTask;
import com.wanmi.sbc.mq.delay.core.Timer;
import com.wanmi.sbc.mq.delay.dao.MqDelayRepository;
import com.wanmi.sbc.mq.producer.CommonProducerService;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanggaolei
 * @className DelayService
 * @description TODO
 * @date 2021/9/14 4:46 下午
 */
@Slf4j
@Service
public class DelayService {


    @Autowired private MqDelayRepository repository;

    @Autowired private RedisTemplate<String, String> redisTemplate;

    @Autowired private CommonProducerService producerService;

    @Autowired private DelayProperties delayProperties;

    @Autowired private RedissonClient redissonClient;

    private static final String DELAY_PROCESS_KEY = "delay_process:";


    /**
     * 持久化并且加入时间轮盘
     *
     * @param sendDelay
     * @return
     */
    public boolean add(MqSendDelayDTO sendDelay) {
        Long id = flush(sendDelay);
        return this.addTimer(id, sendDelay.getDelayTime());
    }

    /**
     * 将任务加入时间轮
     *
     * @param id
     * @param delayTime
     * @return
     */
    public boolean addTimer(Long id, Long delayTime) {
        Timer.getInstance().addTask(new TimedTask(delayTime, new Task(id)));
        return true;
    }

    @SneakyThrows
    private long flush(MqSendDelayDTO sendDelay) {
        MqDelay mqDelay = new MqDelay();

        mqDelay.setTopic(sendDelay.getTopic());
        mqDelay.setTtl(sendDelay.getDelayTime());
        mqDelay.setCreateTime(LocalDateTime.now());
        mqDelay.setExprTime(
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(System.currentTimeMillis() + sendDelay.getDelayTime()),
                        ZoneId.systemDefault()));
        mqDelay.setServerIp(MqConstant.IP);
        mqDelay.setStatus(0);
        mqDelay.setTraceId(TraceContext.getTraceId());
        String stringData;
        if (sendDelay.getData() instanceof String) {
            stringData = (String) sendDelay.getData();
        } else {
            stringData = JSONObject.toJSONString(sendDelay.getData());
        }
        mqDelay.setData(stringData);

        if (delayProperties.getFlushMode().isRedis()) {
            mqDelay.setId(flushCache(mqDelay));
        } else {
            mqDelay.setId(flushDB(mqDelay));
        }
        return mqDelay.getId();
    }

    private long flushDB(MqDelay mqDelay) {

        repository.save(mqDelay);
        return mqDelay.getId();
    }

    private long flushCache(MqDelay mqDelay) {
        String prefix = DateUtil.format(LocalDateTime.now(), "yyMMdd");
        long id = redisTemplate.opsForValue().increment(RedisConstant.DELAY_ID + prefix);
        mqDelay.setId(id);
        if (id == 1 || id % 100 == 0) {
            redisTemplate.expire(
                    RedisConstant.DELAY_ID + prefix, getSecondNumber(), TimeUnit.SECONDS);
        }
        String key = RedisConstant.DELAY_DATA + prefix + id;
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(mqDelay));
        redisTemplate
                .opsForZSet()
                .add(
                        RedisConstant.DELAY_ZSET_IP,
                        key,
                        mqDelay.getExprTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        return Long.parseLong("" + prefix + id);
    }

    private void process(long id) {
        String lockKey = DELAY_PROCESS_KEY + id;
        RLock rLock = redissonClient.getFairLock(lockKey);
        rLock.lock();
        try {
            if (delayProperties.getFlushMode().isRedis()) {
                cacheProcess(id);
            } else {
                dbProcess(id);
            }
        } catch (Exception e) {
            log.error("延迟队列任务执行失败，id: {}", id, e);
        } finally {
            rLock.unlock();
        }
    }

    private void dbProcess(long id) {
        MqDelay mqDelay = repository.findById(id).orElse(null);
        if (mqDelay != null && mqDelay.getStatus() != 1) {
            MqSendDTO sendDTO = new MqSendDTO();
            sendDTO.setTopic(mqDelay.getTopic());
            sendDTO.setData(mqDelay.getData());
            producerService.send(sendDTO, mqDelay.getTraceId());

            mqDelay.setStatus(1);
            mqDelay.setSendTime(LocalDateTime.now());
            repository.save(mqDelay);
            log.info("延迟队列发送成功：{}", mqDelay);
        }
    }

    protected void cacheProcess(long id) {

        this.cacheProcess(id, RedisConstant.DELAY_ZSET_IP);
    }

    protected void cacheProcess(long id, String zsetKey) {
        String key = RedisConstant.DELAY_DATA + id;
        MqDelay mqDelay =
                JSONObject.parseObject(redisTemplate.opsForValue().get(key), MqDelay.class);
        if (mqDelay != null && mqDelay.getStatus() != 1) {

            MqSendDTO sendDTO = new MqSendDTO();
            sendDTO.setTopic(mqDelay.getTopic());
            sendDTO.setData(mqDelay.getData());
            producerService.send(sendDTO, mqDelay.getTraceId());
            log.info("延迟队列发送成功：{}", mqDelay);
        }
        redisTemplate.opsForZSet().remove(zsetKey, key);
        redisTemplate.delete(key);
    }

    class Task implements Runnable {
        private long id;

        public Task(long id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                log.info("延迟时间到了:{}", id);
                process(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 获取当前时间，到凌晨 相差的秒数
    private long getSecondNumber() {

        LocalDateTime todayMax = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        // 往后推60s，防止时间重叠
        long seconds =
                TimeUnit.NANOSECONDS.toSeconds(
                        Duration.between(LocalDateTime.now(), todayMax).toNanos())
                        + 60;
        return seconds;
    }

    public DelayProperties.FlushMode getFlushType() {
        return delayProperties.getFlushMode();
    }
}