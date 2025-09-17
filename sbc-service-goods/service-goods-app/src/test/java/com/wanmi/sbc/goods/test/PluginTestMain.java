package com.wanmi.sbc.goods.test;

import com.wanmi.sbc.goods.plugin.*;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhanggaolei
 * @className PluginTestMain
 * @description
 * @date 2022/10/19 17:31
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PluginTestMain {
    @Resource
    private PluginTestService pluginTestService;

    @Test
    public void testMain(){
        pluginTestService.test("123");
    }
}
