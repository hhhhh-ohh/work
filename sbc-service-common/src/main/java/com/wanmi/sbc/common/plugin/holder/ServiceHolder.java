package com.wanmi.sbc.common.plugin.holder;

import com.wanmi.sbc.common.plugin.annotation.Routing;
import com.wanmi.sbc.common.util.ReflectUtils;
import org.springframework.aop.support.AopUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Service包装类，用于添加一些额外属性
 * @author EDZ
 * @className ServiceHolder
 * @date 2021/6/25 9:37
 **/
public class ServiceHolder<T> {

    private T service;

    public ServiceHolder(T service){
        this(service, false);
    }

    public ServiceHolder(T service, boolean initMethod) {
        this.service = service;
        if(Objects.nonNull(service) && initMethod){
            Class<?> targetClass = AopUtils.getTargetClass(service);
            ReflectUtils.doWithMethods(targetClass, method -> {
                methodAnnoAttrMap.put(method.getName(), new MethodAnnoAttr(ReflectUtils
                        .getMethodMatchAnnotation(method, targetClass,Routing.class)));
            }, method -> {
                ReflectUtils.makeAccessible(method);
                return Objects.nonNull(method.getAnnotation(Routing.class));
            });
        }
    }

    public T getService() {
        return service;
    }

    /***
     * 方法-属性映射，用于减少反射的性能消耗
     */
    private Map<String,MethodAnnoAttr> methodAnnoAttrMap = new HashMap<>(8);

    /***
     * 方法执行参数是否匹配EL表达式
     * @param methodName    方法名称
     * @param args          执行参数
     * @return
     */
    public boolean matchEl(String methodName, Object[] args) {
        return methodAnnoAttrMap.containsKey(methodName)
                && methodAnnoAttrMap.get(methodName).matchEl(args,service.getClass().getName(),methodName);
    }

    /***
     * 获得指定方法的注解属性
     * @param methodName 方法名
     * @return           方法Routing缓存注解属性
     */
    public MethodAnnoAttr getMethodAnnoAttr(String methodName){
        return methodAnnoAttrMap.containsKey(methodName) ?
                methodAnnoAttrMap.get(methodName) : null;
    }
}
