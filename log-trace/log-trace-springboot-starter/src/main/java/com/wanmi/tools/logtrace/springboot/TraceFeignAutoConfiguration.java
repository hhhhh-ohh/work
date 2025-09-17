package com.wanmi.tools.logtrace.springboot;

import com.wanmi.tools.logtrace.channel.feign.TraceFeignFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanggaolei
 * @className TraceFeignAutoConfiguration
 * @description TODO
 * @date 2021/12/15 5:59 下午
 **/
@Configuration
@ConditionalOnClass(name = {"feign.RequestInterceptor"})
public class TraceFeignAutoConfiguration {
    @Bean
    public TraceFeignFilter traceFeignFilter() {
        return new TraceFeignFilter();
    }
}
