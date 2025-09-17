package com.wanmi.tools.logtrace.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhanggaolei
 * @description TODO
 * @date 2021/12/15 2:42 下午
 **/
public class SpanIdGenerator {

    private static volatile TransmittableThreadLocal<String> currentSpanIdTL =
            new TransmittableThreadLocal<>();

    private static volatile TransmittableThreadLocal<AtomicInteger> spanIndex =
            new TransmittableThreadLocal<>();

    protected static void putSpanId(String spanId) {
        if (StringUtils.isBlank(spanId)) {
            spanId = "0";
        }
        currentSpanIdTL.set(spanId);
        spanIndex.set(new AtomicInteger(0));
    }

    protected static String getSpanId() {
        return currentSpanIdTL.get();
    }

    protected static void removeSpanId() {
        currentSpanIdTL.remove();
    }

    protected static String generateNextSpanId() {
        String currentSpanId = TraceContext.getSpanId();
        int currentSpanIndex = spanIndex.get().incrementAndGet();
        return  new StringBuilder(currentSpanId).append(".").append(currentSpanIndex).toString();
    }
}
