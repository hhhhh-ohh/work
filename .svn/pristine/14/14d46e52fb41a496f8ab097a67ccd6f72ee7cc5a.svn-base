package com.wanmi.sbc.common.plugin.handler;

import com.wanmi.sbc.common.plugin.holder.ServiceProxyCaching;
import com.wanmi.sbc.common.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 初始化所有代理方法
 * @author zhengyang
 * @className RoutingInitHandler
 * @date 2021/6/25 10:11
 **/
@Slf4j
public class RoutingInitHandler implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ServiceProxyCaching.loopMap((service)->{
            Field field = ReflectUtils.findField(service.getValue().getClass(),"h");
            if(Objects.nonNull(field)){
                ReflectUtils.makeAccessible(field);
                try {
                    RoutingProxyHandler handler = (RoutingProxyHandler) field.get(service.getValue());
                    handler.init();
                } catch (IllegalAccessException e) {
                    log.error("{} Proxy Init error!",service.getValue().getClass().getName());
                }
            }
        });
    }
}
