package com.wanmi.tools.logtrace.springboot;

import com.wanmi.tools.logtrace.core.handler.TraceAnnotationAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanggaolei
 * @className TraceAopAutoConfiguration
 * @description TODO
 * @date 2021/12/15 5:59 下午
 */
@Configuration
public class TraceAopAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(TraceAnnotationAop.class)
    public TraceAnnotationAop traceAnnotationAop() {
        return new TraceAnnotationAop();
    }
}
