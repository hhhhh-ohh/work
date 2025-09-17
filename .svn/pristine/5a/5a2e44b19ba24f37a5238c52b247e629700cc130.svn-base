package com.wanmi.tools.logtrace.channel.message.rocket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.InterceptableChannel;

/**
 * @author apple1
 * @className TraceRocketBeanPostProcessor
 * @description TODO
 * @date 2024/1/23 14:59
 **/
public class TraceRocketBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 处理消息通道相关的
        if (bean instanceof MessageChannel) {
            MessageChannel channel = (MessageChannel) bean;
            // 添加拦截器
            addInterceptor(channel);
        }

        return bean;
    }

    private void addInterceptor(MessageChannel channel) {
        // 添加消息拦截器
        if (channel instanceof InterceptableChannel) {
            ChannelInterceptor interceptor = new TraceRocketMessageInterceptor();
            ((InterceptableChannel) channel).addInterceptor(interceptor);
        }
    }
}