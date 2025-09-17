package com.wanmi.sbc.aop;

import com.alibaba.fastjson2.JSONArray;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.elastic.api.provider.base.EsBaseProvider;
import com.wanmi.sbc.elastic.api.request.base.EsDeleteByIdAndIndexNameRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


/**
 * Es删除处理器
 */
@Order(0)
@Aspect
@Component
@Slf4j
public class EsDeleteAspect {

    @Autowired
    private EsBaseProvider esBaseProvider;

    @Pointcut("execution(* com.wanmi.sbc..*Provider.*(..))")
    public void pointcutLock() {
    }

    private static final Set<String> excludedMethods = Collections.singleton("uploadFile");

    @AfterReturning(pointcut = "pointcutLock()", returning = "res")
    public void after(JoinPoint joinPoint, Object res) throws SbcRuntimeException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        String str = String.format("请求微服务模块 --> 类名：%s --> 方法名： %s() ",
                targetMethod.getDeclaringClass().getSimpleName(), joinPoint.getSignature().getName());
        String requestInfo = DateUtil.nowTime() + str + "请求参数：" + getMessage(joinPoint);
        String errorData = Objects.toString(((BaseResponse) res).getErrorData(), "");
        if (StringUtils.isNotBlank(errorData)) {
            log.error("Es删除处理器-ES关键信息出现找不到异常！请求的接口信息：{}，ES关键信息：{}", requestInfo, errorData);
            String[] data = errorData.split(":");
            if (data.length == Constants.TWO && StringUtils.isNotBlank(data[0]) && StringUtils.isNotBlank(data[1])) {
                log.info("Es删除处理器-ES关键信息-ES关键信息：{}--->删除进行中", errorData);
                EsDeleteByIdAndIndexNameRequest request = new EsDeleteByIdAndIndexNameRequest();
                request.setIndexName(data[0]);
                request.setId(data[1]);
                try {
                    esBaseProvider.deleteByIdAndIndex(request);
                } catch (Exception e) {
                    log.error("Es删除处理器-ES关键信息-删除出现异常", e);
                }
            }
        }
    }

    /**
     * 获取异常信息
     *
     * @param point
     * @return
     */
    private String getMessage(JoinPoint point) {
        String message = "业务特殊处理, 忽略请求参数!";
        if (!excludedMethods.contains(point.getSignature().getName())) {
            message = JSONArray.toJSONString(Arrays.asList(point.getArgs()));
        }
        return message;
    }
}
