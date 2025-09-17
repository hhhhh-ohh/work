package com.wanmi.tools.logtrace.channel.web;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhanggaolei
 * @className TraceWebConfig
 * @description TODO
 * @date 2021/12/15 4:08 下午
 */
public class TraceWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration;
        interceptorRegistration = registry.addInterceptor(new TraceWebInterceptor());
        interceptorRegistration.order(Ordered.HIGHEST_PRECEDENCE);

    }
}
