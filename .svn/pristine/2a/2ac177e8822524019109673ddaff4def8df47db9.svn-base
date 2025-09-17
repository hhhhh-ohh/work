package com.wanmi.sbc.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 反射工具类，基于Spring工具类的增强
 * @author zhengyang
 * @className ReflectUtils
 * @date 2021/5/24 16:26
 **/
@Slf4j
public final class ReflectUtils extends ReflectionUtils {

    private ReflectUtils(){
    }

    /***
     * 判断是否实现了该接口
     * @param cls   类型
     * @param name  接口全限定名
     * @param <T>   泛型
     * @return      是否实现了指定的接口
     */
    public static <T> boolean hasInterface(Class<T> cls, String name) {
        if (Objects.isNull(cls) || Objects.isNull(name)) {
            return false;
        }
        return hasInterface(cls, Collections.singletonList(name));
    }

    /***
     * 判断是否实现了该接口
     * @param cls   类型
     * @param names  接口全限定名
     * @param <T>   泛型
     * @return      是否实现了指定的接口
     */
    public static <T> boolean hasInterface(Class<T> cls, List<String> names) {
        if (CollectionUtils.isEmpty(names)) {
            return false;
        }
        List<String> interFaceNames = new ArrayList<>();
        Class loopCls = cls;
        while (Objects.nonNull(loopCls)) {
            interFaceNames.addAll(
                    Arrays.stream(loopCls.getInterfaces())
                            .map(Class::getName)
                            .collect(Collectors.toList()));

            interFaceNames.addAll(
                    Arrays.stream(loopCls.getInterfaces())
                            .flatMap(a -> Arrays.stream(a.getInterfaces()).map(Class::getName))
                            .collect(Collectors.toList()));
            loopCls = loopCls.getSuperclass();
        }
        return CollectionUtils.containsAny(interFaceNames, names);
    }

    /***
     * 获得类上的注解
     * @param instance   类对应的Class
     * @param annotation 注解对应的Class
     * @param <T>        注解泛型
     * @return           注解
     */
    public static <T extends Annotation,R> T getAnnotation(Class<R> instance,Class<T> annotation){
        if (Objects.isNull(instance) || Objects.isNull(annotation)) {
            return null;
        }
        Object anno = instance.getAnnotation(annotation);
        if (Objects.nonNull(anno)) {
            return (T) anno;
        }
        return getAnnotation(instance.getSuperclass(), annotation);
    }

    /***
     * 获得和Method对象匹配的对象上的注解
     * @param method     方法对象
     * @param instance   类对应的Class
     * @param annotation 注解对应的Class
     * @param <T>        注解泛型
     * @return           注解
     */
    public static <T extends Annotation, R> T getMethodMatchAnnotation(Method method, Class<R> instance, Class<T> annotation) {
        if (Objects.isNull(method) || Objects.isNull(instance) || Objects.isNull(annotation)) {
            return null;
        }
        Method matchMethod = findMethod(instance, method.getName(), method.getParameterTypes());
        if (Objects.nonNull(matchMethod)) {
            Object anno = matchMethod.getAnnotation(annotation);
            if (Objects.nonNull(anno)) {
                return (T) anno;
            }
        }
        return getMethodMatchAnnotation(method, instance.getSuperclass(), annotation);
    }



    /***
     * 设置字段值，如果是代理类，找到被代理对象
     * @param instance  实例对象
     * @param field     字段对象
     * @param val       设置的值
     * @param <T>       泛型
     */
    public static <T> void setFieldVal(T instance, Field field, Object val) {
        // 判断是否Cglib代理
        if (AopUtils.isCglibProxy(instance)) {
            // CallBack字段
            Field callbackField = findField(instance.getClass(), "CGLIB$CALLBACK_0");
            makeAccessible(callbackField);
            // 拦截器字段
            Object dynamicAdvisedInterceptor = getField(callbackField,instance);
            Field advised = findField(dynamicAdvisedInterceptor.getClass(),"advised");
            makeAccessible(advised);
            try {
                Object target = ((AdvisedSupport) getField(advised,dynamicAdvisedInterceptor)).getTargetSource().getTarget();
                setField(field, target, val);
            } catch (Exception e) {
                log.error("ReflectUtils setFieldVal error !", e);
            }

        } else {
            setField(field, instance, val);
        }
    }
}
