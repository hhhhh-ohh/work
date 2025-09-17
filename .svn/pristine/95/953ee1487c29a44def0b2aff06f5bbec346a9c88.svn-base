package com.wanmi.tools.logtrace.core.handler;

import com.wanmi.tools.logtrace.core.bean.TraceBean;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author zhanggaolei
 * @className TraceAnnotationAcp
 * @description TODO
 * @date 2021/12/15 5:39 下午
 **/
@Aspect
public class TraceAnnotationAop {

    @Pointcut("@annotation(com.wanmi.tools.logtrace.core.annotation.Trace)")
    public void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceBean traceBean = new TraceBean();
        TraceHandler.process(traceBean);
        Object object = joinPoint.proceed();
        TraceHandler.clean();
        return object;
    }
}
