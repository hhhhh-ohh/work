package com.wanmi.tools.logtrace.channel.xxljob;

import com.wanmi.tools.logtrace.core.bean.TraceBean;
import com.wanmi.tools.logtrace.core.handler.TraceHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author zhanggaolei
 * @className TraceXxlJobAop
 * @description TODO
 * @date 2021/12/16 10:11 上午
 **/
@Aspect
public class TraceXxlJobAop {
    @Pointcut("@annotation(com.xxl.job.core.handler.annotation.XxlJob)")
    public void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            TraceHandler.process(new TraceBean());
            return joinPoint.proceed();
        }finally {
            TraceHandler.clean();
        }
    }
}
