package com.wanmi.tools.logtrace.channel.message;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.integration.channel.AbstractMessageChannel;

import java.lang.reflect.Method;

/**
 * @author zhanggaolei
 * @className TraceRabbitBeanPostProcessor
 * @description TODO
 * @date 2021/12/20 1:50 下午
 **/
public class TraceRabbitBeanPostProcessor implements BeanPostProcessor {

    private TraceRabbitHandler traceRabbitMqHandler;
    private AbstractBeanFactory beanFactory;

    public TraceRabbitBeanPostProcessor(){

    }

    public TraceRabbitBeanPostProcessor(AbstractBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof RabbitTemplate) {
            return rabbitMqHandler().decorateRabbitTemplate((RabbitTemplate) bean);
        }
        else if (bean instanceof SimpleRabbitListenerContainerFactory) {
            return rabbitMqHandler().decorateSimpleRabbitListenerContainerFactory(
                    (SimpleRabbitListenerContainerFactory) bean);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof AbstractMessageChannel) {
            try {
                RootBeanDefinition beanDefinition = (RootBeanDefinition) beanFactory.getMergedBeanDefinition(beanName);
                Method method = beanDefinition.getResolvedFactoryMethod();

//                if (method != null) {
//                    if (AnnotationUtils.findAnnotation(method, Input.class) != null) {
//                        ((AbstractMessageChannel)bean).addInterceptor(new TraceMessageInputInterceptor());
//                    }
//                    if (AnnotationUtils.findAnnotation(method, Output.class) != null) {
//                        ((AbstractMessageChannel) bean)
//                                .addInterceptor(new TraceMessageOutputInterceptor());
//                        }
//                }
            } catch (Exception e) {
                // exception can be thrown by the bean factory
            }
        }


        return bean;
    }

    synchronized TraceRabbitHandler rabbitMqHandler(){
        if (this.traceRabbitMqHandler == null){
            this.traceRabbitMqHandler = new TraceRabbitHandler();
        }
        return traceRabbitMqHandler;
    }
}
