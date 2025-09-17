package com.wanmi.sbc.common.exception;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Order(1)
@Aspect
@Component
@Slf4j
public class ClientCallAspect {

    /**
     * 排除掉请求参数的方法名
     */
    private static final Set<String> excludedMethods = new HashSet<>(Arrays.asList("uploadFile","uploadPayCertificate","upload"));

    /**
     * 日志输出格式，目标方法
     */
    private static final String LOG_INFO_FORMAT_TARGET_METHOD = "请求微服务模块 --> 类名：%s --> 方法名： %s() ";

    @Value("${common.log.request.maxLength:512}")
    private int requestMaxLength;
    @Value("${common.log.response.maxLength:1024}")
    private int responseMaxLength;

    @Pointcut("execution(* com.wanmi.sbc..*Provider.*(..))")
    public void pointcutLock() {
    }

    @Around(value = "pointcutLock()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        String str = String.format(LOG_INFO_FORMAT_TARGET_METHOD,
                targetMethod.getDeclaringClass().getSimpleName(), joinPoint.getSignature().getName());

        StopWatch stopWatch = new StopWatch(str + System.currentTimeMillis());
        stopWatch.start();
        Object result =  joinPoint.proceed();

        stopWatch.stop();

        log.info("{}请求参数：{}，返回参数：{}，执行时间=======>>{}", str
                , truncString(getMessage(joinPoint), requestMaxLength)
                , truncString(JSONObject.toJSONString(result), responseMaxLength)
                , stopWatch.getTotalTimeMillis());

        return result;
    }

    @AfterReturning(pointcut = "pointcutLock()", returning = "res")
    public void after(JoinPoint joinPoint, Object res) throws SbcRuntimeException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        String str = String.format(LOG_INFO_FORMAT_TARGET_METHOD,
                targetMethod.getDeclaringClass().getSimpleName(), joinPoint.getSignature().getName());
        if (res == null){
            log.info("{}无返回结果^_^======^_^", str);
            return;
        }
        String errCode = ((BaseResponse) res).getCode();
        if(errCode.equals("K-000000")){
            errCode = CommonErrorCodeEnum.K000000.getCode();
        }
        String errMsg = ((BaseResponse) res).getMessage();
        Object context = ((BaseResponse) res).getErrorData();
        if (!CommonErrorCodeEnum.K000000.getCode().equals(errCode)) {
            String requestInfo = str + "请求参数：" + getMessage(joinPoint);
            log.error("{}出现异常！请求的接口信息：{}，接口返回信息：{}", str, requestInfo, res);
            if (context != null) {
                if (StringUtils.isEmpty(errMsg)){
                    throw new SbcRuntimeException(context, errCode);
                }else{
                    throw new SbcRuntimeException(context, errCode, errMsg);
                }
            } else {
                throw new SbcRuntimeException(errCode, errMsg);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("{}返回参数：{}", str, JSONObject.toJSONString(res));
        }
    }

    @Before("pointcutLock()")
    public void before(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method targetMethod = methodSignature.getMethod();
        if (log.isDebugEnabled()) {
            String str = String.format(LOG_INFO_FORMAT_TARGET_METHOD,
                    targetMethod.getDeclaringClass().getSimpleName(), point.getSignature().getName());
            String requestInfo = str + "请求参数：" + getMessage(point);
            log.debug(requestInfo);
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
            message = JSONObject.toJSONString(point.getArgs());
        }
        return message;
    }

    private static String truncString(String str, int maxLength) {
        if(maxLength < 0){
            return str;
        }
        if(maxLength == 0){
            return null;
        }
        if (str == null) {
            return null;
        } else if (str.length() > maxLength) {
            String subSuffix = String.format("...(source length is %s)", str.length());
            return str.substring(0, maxLength - subSuffix.length()) + subSuffix;
        } else {
            return str;
        }
    }
}
