package com.wanmi.tools.logtrace.channel.feign;

import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhanggaolei
 * @className TraceFeignFilter
 * @description TODO
 * @date 2021/12/15 5:48 下午
 **/
public class TraceFeignFilter implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = TraceContext.getTraceId();
        if (StringUtils.isNotBlank(traceId)) {
            requestTemplate.header(TraceConstants.KEY_TRACE_ID, TraceContext.getTraceId());
            requestTemplate.header(TraceConstants.KEY_SPAN_ID, TraceContext.generateNextSpanId());
            requestTemplate.header(TraceConstants.KEY_APP_NAME, appName);
        }
    }
}
