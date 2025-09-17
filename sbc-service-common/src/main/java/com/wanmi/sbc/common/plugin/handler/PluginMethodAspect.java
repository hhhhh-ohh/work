package com.wanmi.sbc.common.plugin.handler;

import com.wanmi.sbc.common.plugin.annotation.PluginMethod;
import com.wanmi.sbc.common.plugin.bean.PluginInvoker;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * @author zhanggaolei
 * @className PluginMethodAspect
 * @description TODO
 * @date 2021/8/7 10:19 上午
 **/
@Aspect
@Component
@ConditionalOnProperty(prefix = "wanmi.plugin", name = "enabled", havingValue = "true", matchIfMissing = false)
public class PluginMethodAspect {

    @Pointcut("@annotation(com.wanmi.sbc.common.plugin.annotation.PluginMethod)")
    private void pointcut() {}

    @Around("pointcut() && @annotation(method)")
    public Object process(ProceedingJoinPoint joinPoint, PluginMethod method) throws Throwable {
        Object result = null;
        if(method.main()){
            //类名
            String className=joinPoint.getSignature().getDeclaringType().getName();
            //方法名
            String methodName= joinPoint.getSignature().getName();
            String tagName= className+"."+methodName;
            Object[] args = joinPoint.getArgs();
            List<PluginInvoker> list = PluginMethodScanner.invokerMap.get(tagName);
            if(CollectionUtils.isNotEmpty(list)){
                list.sort(Comparator.comparing(PluginInvoker::getOrder));
                for(PluginInvoker invoker : list){
                    if(invoker.getTag().equals(tagName)){
                        result = joinPoint.proceed();
                    } else {
                        result = invoker.getMethod().invoke(invoker.getBean(), args);
                    }
                    if(invoker.isReplace()){
                        break;
                    }
                }
            }
        }else {
            result = joinPoint.proceed();
        }
        return result;
    }
}
