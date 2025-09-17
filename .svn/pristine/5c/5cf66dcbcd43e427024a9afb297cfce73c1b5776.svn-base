package com.wanmi.tools.logtrace.channel.message.rocket;

import com.wanmi.tools.logtrace.core.bean.TraceBean;
import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.handler.TraceHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * @author apple1
 * @className TraceReceiveMessageInterceptor
 * @description TODO
 * @date 2024/1/23 14:55
 **/
public class TraceRocketMessageInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        String traceId =
                message.getHeaders().get(TraceConstants.KEY_TRACE_ID) == null
                        ? null
                        : String.valueOf(message.getHeaders().get(TraceConstants.KEY_TRACE_ID));
        String spanId =
                message.getHeaders().get(TraceConstants.KEY_SPAN_ID) == null
                        ? null
                        : String.valueOf(message.getHeaders().get(TraceConstants.KEY_SPAN_ID));
        TraceBean traceBean = new TraceBean(traceId,spanId);
        TraceHandler.process(traceBean);
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel,
                                    boolean sent, Exception ex) {
        TraceHandler.clean();
    }
}