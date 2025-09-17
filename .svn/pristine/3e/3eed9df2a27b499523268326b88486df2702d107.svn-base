package com.wanmi.tools.logtrace.channel.message;

import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * @author zhanggaolei
 * @className TraceMessagePostProcessor
 * @description TODO
 * @date 2021/12/20 11:46 上午
 **/
public class TraceMessagePostProcessor implements MessagePostProcessor {
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setHeader(TraceConstants.KEY_TRACE_ID, TraceContext.getTraceId());
        message.getMessageProperties().setHeader(TraceConstants.KEY_SPAN_ID,TraceContext.generateNextSpanId());
        return message;
    }
}
