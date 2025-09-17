package com.wanmi.tools.logtrace.core.handler;

import com.wanmi.tools.logtrace.core.bean.TraceBean;
import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author zhanggaolei
 * @className TraceHandler
 * @description TODO
 * @date 2021/12/15 4:33 下午
 **/
public class TraceHandler {

    public static void process(TraceBean traceBean) {

        //如果没有获取到，则重新生成一个traceId
        if (StringUtils.isBlank(traceBean.getTraceId())) {
            traceBean.setTraceId(TraceContext.getNoneTraceId());
        }

        //如果spanId为空，会放入初始值
        TraceContext.putSpanId(traceBean.getSpanId());

        TraceContext.putTraceId(traceBean.getTraceId());

        MDC.put(TraceConstants.KEY_TRACE_ID,TraceContext.getTraceId());
        MDC.put(TraceConstants.KEY_SPAN_ID,TraceContext.getSpanId());
    }

    public static void clean() {

        TraceContext.removeTraceId();
        TraceContext.removeSpanId();

    }
}
