package com.wanmi.tools.logtrace.springboot;

import com.wanmi.tools.logtrace.channel.message.TraceRabbitBeanPostProcessor;
import com.wanmi.tools.logtrace.channel.message.TraceRabbitHandler;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author zhanggaolei
 * @className TraceMessageConfiguration
 * @description TODO
 * @date 2021/12/17 4:54 下午
 **/
@Configuration
@ConditionalOnClass(RabbitTemplate.class)
public class TraceMessageConfiguration {

    @Bean
    @ConditionalOnMissingBean
    static TraceRabbitBeanPostProcessor traceRabbitBeanPostProcessor(AbstractBeanFactory beanFactory) {
        return new TraceRabbitBeanPostProcessor(beanFactory);
    }
}
