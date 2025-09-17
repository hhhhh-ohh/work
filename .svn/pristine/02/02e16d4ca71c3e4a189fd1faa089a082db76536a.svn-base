package com.wanmi.tools.logtrace.channel.web;

import com.wanmi.tools.logtrace.core.bean.TraceBean;
import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.handler.TraceHandler;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhanggaolei
 * @className TraceWebFilter
 * @description TODO
 * @date 2021/12/16 10:49 上午
 **/
public class TraceWebFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(TraceConstants.KEY_TRACE_ID);
        String spanId = request.getHeader(TraceConstants.KEY_SPAN_ID);

        TraceBean traceBean = new TraceBean(traceId, spanId);
        TraceHandler.process(traceBean);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
