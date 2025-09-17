package com.wanmi.tools.logtrace.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.apache.commons.lang3.StringUtils;


/**
 * @author zhanggaolei
 * @description TODO
 * @date 2021/12/15 2:42 下午
 *
**/
public class TraceContext {

    private static final TransmittableThreadLocal<String> TRACE_ID = new TransmittableThreadLocal<>();

    public static void putTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static String getTraceId() {
        return TRACE_ID.get();
    }

    public static String getNoneTraceId(){
        String traceId = TRACE_ID.get();
        if(StringUtils.isBlank(traceId)){
            traceId = TraceIdGenerator.generatorTraceId();
            putTraceId(traceId);
        }
        return traceId;
    }

    public static void removeTraceId() {
        TRACE_ID.remove();
    }

    public static void putSpanId(String spanId) {
        SpanIdGenerator.putSpanId(spanId);
    }

    public static String getSpanId() {
        return SpanIdGenerator.getSpanId();
    }

    public static String generateNextSpanId() {
        return SpanIdGenerator.generateNextSpanId();
    }

    public static void removeSpanId() {
        SpanIdGenerator.removeSpanId();
    }


}
