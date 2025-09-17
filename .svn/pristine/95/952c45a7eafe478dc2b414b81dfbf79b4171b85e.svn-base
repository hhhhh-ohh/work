package com.wanmi.tools.logtrace.springboot;

import com.wanmi.tools.logtrace.channel.message.rocket.TraceRocketBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author apple1
 * @className TraceRocketAutoConfiguration
 * @description TODO
 * @date 2024/1/18 15:07
 **/
@Configuration
@ConditionalOnClass(BindingService.class)
public class TraceRocketAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TraceRocketBeanPostProcessor traceRocketBeanPostProcessor() {
        return new TraceRocketBeanPostProcessor();
    }

}