package com.wanmi.sbc.mq.provider.impl;

import com.wanmi.sbc.mq.delay.service.DelayJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author zhanggaolei
 * @className TestController
 * @description
 * @date 2022/6/10 09:59
 **/
@RestController
public class TestController {

    @Autowired
    DelayJobService delayJobService;

    @RequestMapping(method=GET,value = "test")
    public void test() {
        delayJobService.process();
    }
}
