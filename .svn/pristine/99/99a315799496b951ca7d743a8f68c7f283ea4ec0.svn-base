package com.wanmi.tools.logtrace.channel.message;

import com.wanmi.tools.logtrace.core.bean.TraceBean;
import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.handler.TraceHandler;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * @author zhanggaolei
 * @className TraceMessageProcessor
 * @description TODO
 * @date 2021/12/17 3:45 下午
 */
public class TraceMessageInputInterceptor implements ChannelInterceptor {

//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        message.getHeaders().put(Constants.KEY_TRACE_ID, TraceContext.getTraceId());
//        message.getHeaders().put(Constants.KEY_SPAN_ID, TraceContext.generateNextSpanId());
//        return message;
//    }

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
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
//        System.out.println("after");
        TraceHandler.clean();
    }
}
