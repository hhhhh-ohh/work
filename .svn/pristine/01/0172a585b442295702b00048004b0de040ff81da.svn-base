package com.wanmi.sbc.marketing.util.common;

import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lvzhenwei
 * @className ApplicationClosedEventListener
 * @description TODO
 * @date 2022/12/22 3:08 下午
 **/
@Component
@Slf4j
public class ApplicationClosedEventListener implements DisposableBean {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void destroy() throws Exception {
        log.info("destroy event...");
        String lockKey = String.format(Constant.SNOW_FLAK_ID_KEY, Constant.SERVICE_ID, Constant.MID);
        // 这里直接删除key，不用redisson的unlock，因为可能导致 "attempt to unlock lock, not locked by current thread by node id"
        redisUtil.delete(lockKey);
        log.info("delete SnowFlak MID success，lockKey:{}", lockKey);
    }
}
