package com.wanmi.sbc.account.credit.aspect;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.request.credit.CreditStateChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Objects;

/***
 * CreditOverview接口入参校验切面
 * @author zhengyang
 * @since 2021/3/12 11:04
 */
@Slf4j
@Aspect
public class CreditOverviewCheckAspect {

    /**
     * 定义前置切面
     * 校验所有CreditOverviewService类下方法
     *
     * @param join
     * @throws Throwable 方法执行报错
     */
    @Around("execution(* com.wanmi.sbc.account.credit.service.CreditOverviewService.*(..))")
    public void before(ProceedingJoinPoint join) throws Throwable {
        // 获取方法名
        String methodName = join.getSignature().getName();
        // 获取参数列表
        if (join.getArgs().length == 1) {
            if (Objects.isNull(join.getArgs()[0])) {
                log.warn("CreditOverviewCheckAspect interpret {} break,params is empty!", methodName);
                return;
            }
            if (join.getArgs()[0] instanceof CreditStateChangeEvent) {
                CreditStateChangeEvent event = (CreditStateChangeEvent) join.getArgs()[0];
                if (Objects.isNull(event.getCreditStateChangeType()) || Objects.isNull(event.getAmount())) {
                    log.warn("CreditOverviewCheckAspect interpret {} break,params is {}!",
                            methodName, JSON.toJSONString(event));
                    return;
                }
            }
            join.proceed(join.getArgs());
            return;
        }
        log.info("CreditOverviewCheckAspect interpret {} nothing to do ", methodName);
    }

}
