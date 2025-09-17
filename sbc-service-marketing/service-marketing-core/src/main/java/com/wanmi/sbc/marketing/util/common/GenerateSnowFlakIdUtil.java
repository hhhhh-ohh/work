package com.wanmi.sbc.marketing.util.common;

import com.wanmi.sbc.marketing.bean.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lvzhenwei
 * @className GenerateSnowFlakIdUtil
 * @description 微服务生成唯一id
 * @date 2022/12/22 2:34 下午
 **/
@Component
@Order(1)
@Slf4j
public class GenerateSnowFlakIdUtil implements CommandLineRunner {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired private RedissonClient redissonClient;

    @Autowired private MarketingGeneratorService generatorService;

    @Override
    public void run(String... args) throws Exception {
        int mid = RANDOM.nextInt(64);
        String lockKey = String.format(Constant.SNOW_FLAK_ID_KEY,Constant.SERVICE_ID,mid);
        RLock rLock = redissonClient.getFairLock(lockKey);
        try {
            if (rLock.tryLock(1, -1, TimeUnit.SECONDS)) {
                // 首次获取 mid 成功
                Constant.MID = mid;
                // 创建 SnowFlake 实例
                generatorService.dynamicMidSnowFlakeInstance(Constant.SERVICE_ID, mid);
                log.info("init SnowFlak MID success，lockKey:{}", lockKey);
            } else {
                // 首次获取 mid 失败，则开始重试
                newAThread(redissonClient, generatorService);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void newAThread(RedissonClient redissonClient, MarketingGeneratorService generatorService) {
        new Thread(() -> {
            while (true) {
                try {
                    int mid = RANDOM.nextInt(64);
                    String lockKey = String.format(Constant.SNOW_FLAK_ID_KEY,Constant.SERVICE_ID,mid);
                    RLock rLock1 = redissonClient.getFairLock(lockKey);
                    if(rLock1.tryLock(1, -1, TimeUnit.SECONDS)){
                        Constant.MID = mid;
                        // 创建 SnowFlake 实例
                        generatorService.dynamicMidSnowFlakeInstance(Constant.SERVICE_ID, mid);
                        log.info("retry SnowFlak MID success，lockKey:{}", lockKey);
                        break;
                    }
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                    log.error("ex", e);
                }
            }
        }).start();
    }
}
