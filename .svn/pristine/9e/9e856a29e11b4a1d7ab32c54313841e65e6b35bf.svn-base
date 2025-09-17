package com.wanmi.tools.logtrace.channel.message;

import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * @author zhanggaolei
 * @className TraceMessageProcessor
 * @description TODO
 * @date 2021/12/17 3:45 下午
 */
public class TraceMessageOutputInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        message.getHeaders().put(TraceConstants.KEY_TRACE_ID, TraceContext.getTraceId());
        message.getHeaders().put(TraceConstants.KEY_SPAN_ID, TraceContext.generateNextSpanId());
        return message;
    }

}
