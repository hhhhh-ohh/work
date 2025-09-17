package com.wanmi.sbc.init;

import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.setting.bean.enums.SettingRedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 清除部分配置项缓存，防止缓存与页面展示信息不符
 *
 * @author: Geek Wang
 * @createDate: 2019/4/25 10:00
 * @version: 1.0
 */
@Slf4j
@Order(8)
@Component
public class CleanRedisKeyRunner implements CommandLineRunner {

    @Autowired private RedisUtil redisService;

    @Override
    public void run(String... args) {
        try {
			redisService.delete(SettingRedisKey.SYSTEM_POINTS_CONFIG.toValue());
        } catch (Exception e) {
			log.error("redis 信息清理失败, 错误信息", e);
        }
    }
}
