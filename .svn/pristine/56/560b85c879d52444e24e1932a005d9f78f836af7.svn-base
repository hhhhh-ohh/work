package com.wanmi.sbc.mq;

import com.wanmi.sbc.mq.delay.service.DelayJobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhanggaolei
 * @className com.wanmi.sbc.mq.RedisTest
 * @description TODO
 * @date 2021/9/24 10:37 上午
 **/
@SpringBootTest
public class RedisTest {
    @Autowired
    DelayJobService delayJobService;

    @Test
    public void test() {
        delayJobService.process();
    }
}
