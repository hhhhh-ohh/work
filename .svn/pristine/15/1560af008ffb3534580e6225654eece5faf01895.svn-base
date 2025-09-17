package com.wanmi.ares.sensitiveword;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.CheckFunctionDefault;
import com.wanmi.sbc.common.sensitiveword.CheckFunctionInterface;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author wur
 * @className ReturnSensitiveWordsAspect
 * @description TODO
 * @date 2022/7/5 10:18
 **/
@Slf4j
@Aspect
@Component
public class ReturnSensitiveWordsAresAspect {

    @Autowired
    private List<CheckFunctionInterface> checkFunctionList;

    @Pointcut("@annotation(com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords)")
    public void aspectMethod() {

    }

    @Around("aspectMethod()")
    public Object deAfter(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("===============================Ares 脱敏======================================");
        //获取方法的注解
        Class clazz = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = clazz.getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        ReturnSensitiveWords returnSensitiveWords = method.getAnnotation(ReturnSensitiveWords.class);
        Object response = joinPoint.proceed();
        if (Objects.isNull(returnSensitiveWords)) {
            return response;
        }

        //权限处理
        Object[] args = joinPoint.getArgs();
        Operator operator = null;
        if (args.length > 0 && args[0] instanceof Operator) {
            operator = (Operator) args[0];
        }
        CheckFunctionInterface checkFunctionInterface = null;
        for (CheckFunctionInterface checkFunction : checkFunctionList) {
            if (!(checkFunction instanceof CheckFunctionDefault)) {
                checkFunctionInterface = checkFunction;
                break;
            }
        }
        if (Objects.isNull(checkFunctionInterface)) {
            return response;
        }
        if (checkFunctionInterface.checkFunction(operator, Arrays.asList(returnSensitiveWords.functionName()))) {
            return response;
        }

        //处理脱敏
        if (response instanceof BaseResponse) {
            BaseResponse baseResponse = (BaseResponse) response;
            if(Objects.nonNull(baseResponse.getContext())) {
                Object context = baseResponse.getContext();
                if (context instanceof List) {
                    List<Object> contextList = (List<Object>) context;
                    contextList = objectListHandle(contextList);
                    baseResponse.setContext(contextList);
                } else {
                    context = objectHandle(context);
                    baseResponse.setContext(context);
                }
            }
            return baseResponse;
        } else if (response instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity) response;
            Object object = responseEntity.getBody();
            if (object instanceof BaseResponse) {
                BaseResponse baseResponse = (BaseResponse) object;
                if(Objects.nonNull(baseResponse.getContext())) {
                    Object context = baseResponse.getContext();
                    if (context instanceof List) {
                        List<Object> contextList = (List<Object>) context;
                        contextList = objectListHandle(contextList);
                        baseResponse.setContext(contextList);
                    } else {
                        context = objectHandle(context);
                        baseResponse.setContext(context);
                    }
                }
                return ResponseEntity.ok(baseResponse);
            } else if (object instanceof List) {
                List<Object> contextList = (List<Object>) object;
                contextList = objectListHandle(contextList);
                return ResponseEntity.ok(contextList);
            } else {
                object = objectHandle(object);
                return ResponseEntity.ok(object);
            }
        } else if(response instanceof List) {
            List<Object> res = (List<Object>) response;
            return objectListHandle(res);
        } else {
            response = objectHandle(response);
        }
        return response;
    }

    /**
    *
     * @description    处理集合
     * @author  wur
     * @date: 2022/7/19 16:40
     * @param objectList
     * @return
     **/
    private List<Object> objectListHandle(List<Object> objectList) {
        for (Object obj : objectList) {
            try {
                objectHandle(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return objectList;
    }

    /**
     *   处理单对象
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object objectHandle(Object obj) throws IllegalAccessException, InstantiationException {
        Class clazz = obj.getClass();
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            fieldList.addAll(Arrays.asList(fields));
        }
        if (Objects.nonNull(clazz.getSuperclass())) {
            Field[] superFields = clazz.getSuperclass().getDeclaredFields();
            if (superFields.length > 0) {
                fieldList.addAll(Arrays.asList(superFields));
            }
        }


        //获得类指定的注解
        for (Field f : fieldList) {
            //强制属性的访问权限
            f.setAccessible(true);
            if (Objects.isNull(f.get(obj)) || f.get(obj) instanceof Enum) {
                continue;
            }
            Object param = f.get(obj);
            if (param instanceof List) {
                List<Object> parmaList = (List<Object>) param;
                f.set(obj, objectListHandle(parmaList));
            } else {
                if (f.getType().getName().startsWith("com.wanmi.sbc")) {
                    Object field = objectHandle(f.get(obj));
                    f.set(obj, field);
                } else {
                    SensitiveWordsField sensitiveWordsField = f.getAnnotation(SensitiveWordsField.class);
                    if (Objects.nonNull(sensitiveWordsField)) {
                        //获取属性值
                        String word = (String) f.get(obj);
                        String wordSign = sensitiveWordsField.signType().process(word);
                        f.set(obj, wordSign);
                    }
                }
            }
        }
        return obj;
    }

}