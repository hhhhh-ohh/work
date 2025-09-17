package com.wanmi.sbc.goods.provider.impl;

import com.wanmi.sbc.goods.plugin.PluginTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * @author zhanggaolei
 * @className PluginTestController
 * @description
 * @date 2022/10/19 17:55
 **/
@RestController
public class PluginTestController {

    @Resource
    private PluginTestService pluginTestService;

    @GetMapping("/test")
    public void testMain() {
        pluginTestService.test("123");
    }
}
