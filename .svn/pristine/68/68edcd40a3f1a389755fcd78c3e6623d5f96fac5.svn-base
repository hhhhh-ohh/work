package com.wanmi.tools.logtrace.channel.message;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.config.AbstractRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author zhanggaolei
 * @className TraceRabbitHandler
 * @description TODO
 * @date 2021/12/20 1:41 下午
 */
public class TraceRabbitHandler {

    static Field getField(Class<?> clazz, String name) {
        Field result = null;
        try {
            result = clazz.getDeclaredField(name);
            result.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        return result;
    }

    public RabbitTemplate decorateRabbitTemplate(RabbitTemplate rabbitTemplate) {
        MessagePostProcessor[] beforePublishPostProcessors =
                appendTracingMessagePostProcessor(
                        rabbitTemplate,
                        getField(RabbitTemplate.class, "beforePublishPostProcessors"));
        if (beforePublishPostProcessors != null) {
            rabbitTemplate.setBeforePublishPostProcessors(beforePublishPostProcessors);
        }
        return rabbitTemplate;
    }

    public SimpleRabbitListenerContainerFactory decorateSimpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactory factory) {
        Advice[] advice = prependTracingRabbitListenerAdvice(factory);
        if (advice != null) factory.setAdviceChain(advice);

        MessagePostProcessor[] beforeSendReplyPostProcessors =
                appendTracingMessagePostProcessor(
                        factory,
                        getField(
                                AbstractRabbitListenerContainerFactory.class,
                                "beforeSendReplyPostProcessors"));
        if (beforeSendReplyPostProcessors != null) {
            factory.setBeforeSendReplyPostProcessors(beforeSendReplyPostProcessors);
        }

        return factory;
    }

    MessagePostProcessor[] appendTracingMessagePostProcessor(Object obj, Field field) {
        // Skip out if we can't read the field for the existing post processors
        if (field == null) return null;
        MessagePostProcessor[] processors;
        try {
            // don't use "field.get(obj) instanceof X" as the field could be null
            if (Collection.class.isAssignableFrom(field.getType())) {
                Collection<MessagePostProcessor> collection =
                        (Collection<MessagePostProcessor>) field.get(obj);
                processors =
                        collection != null ? collection.toArray(new MessagePostProcessor[0]) : null;
            } else if (MessagePostProcessor[].class.isAssignableFrom(field.getType())) {
                processors = (MessagePostProcessor[]) field.get(obj);
            } else { // unusable field value
                return null;
            }
        } catch (Exception e) {
            return null; // reflection error or collection element mismatch
        }

        TraceMessagePostProcessor tracingMessagePostProcessor = new TraceMessagePostProcessor();
        // If there are no existing post processors, return only the tracing one
        if (processors == null) {
            return new MessagePostProcessor[] {tracingMessagePostProcessor};
        }

        // If there is an existing tracing post processor return
        for (MessagePostProcessor processor : processors) {
            if (processor instanceof TraceMessagePostProcessor) {
                return null;
            }
        }

        // Otherwise, append ours and return
        MessagePostProcessor[] result = new MessagePostProcessor[processors.length + 1];
        System.arraycopy(processors, 0, result, 0, processors.length);
        result[processors.length] = tracingMessagePostProcessor;
        return result;
    }

    Advice[] prependTracingRabbitListenerAdvice(SimpleRabbitListenerContainerFactory factory) {
        Advice[] chain = factory.getAdviceChain();

        TraceRabbitAdvice tracingAdvice = new TraceRabbitAdvice();
        // If there are no existing advice, return only the tracing one
        if (chain == null) return new Advice[] {tracingAdvice};

        // If there is an existing tracing advice return
        for (Advice advice : chain) {
            if (advice instanceof TraceRabbitAdvice) {
                return null;
            }
        }

        // Otherwise, prepend ours and return
        Advice[] result = new Advice[chain.length + 1];
        result[0] = tracingAdvice;
        System.arraycopy(chain, 0, result, 1, chain.length);
        return result;
    }
}
