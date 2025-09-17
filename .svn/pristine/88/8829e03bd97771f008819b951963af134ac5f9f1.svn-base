package com.wanmi.tools.logtrace.channel.web;

import com.wanmi.tools.logtrace.core.bean.TraceBean;
import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import com.wanmi.tools.logtrace.core.handler.TraceHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author zhanggaolei
 * @className TraceWebIntercaptor
 * @description TODO
 * @date 2021/12/15 4:17 下午
 */
public class TraceWebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String traceId = request.getHeader(TraceConstants.KEY_TRACE_ID);
        String spanId = request.getHeader(TraceConstants.KEY_SPAN_ID);

        TraceBean traceBean = new TraceBean(traceId, spanId);
        TraceHandler.process(traceBean);

        response.addHeader(TraceConstants.KEY_TRACE_ID, TraceContext.getTraceId());
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView)
            throws Exception {}

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        TraceHandler.clean();
    }
}
