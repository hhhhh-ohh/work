package com.wanmi.tools.logtrace.channel.message;

import com.wanmi.tools.logtrace.core.bean.TraceBean;
import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.handler.TraceHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.Message;

import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className TraceRabbitAdvice
 * @description TODO
 * @date 2021/12/20 11:25 上午
 **/
public class TraceRabbitAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Message message = null;
        if (methodInvocation.getArguments()[1] instanceof List) {
            message = ((List<? extends Message>) methodInvocation.getArguments()[1]).get(0);
        } else {
            message = (Message) methodInvocation.getArguments()[1];
        }
        Map<String,Object> headers= message.getMessageProperties().getHeaders();

        String traceId = (String)headers.get(TraceConstants.KEY_TRACE_ID);
        String spanId = (String)headers.get(TraceConstants.KEY_SPAN_ID);
        TraceBean traceBean = new TraceBean(traceId,spanId);
        TraceHandler.process(traceBean);
        try {
            return methodInvocation.proceed();
        } catch (Throwable t) {
            throw t;
        }
    }
}
