package com.wanmi.sbc.common.plugin.handler;

//package com.wanmi.sbc.common.plugin.handler;
//
//import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
//import com.wanmi.sbc.common.plugin.holder.ServiceProxyCaching;
//import com.wanmi.sbc.common.util.ReflectUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
//import javax.management.MBeanServer;
//import java.lang.reflect.Proxy;
//import java.util.List;
//import java.util.Objects;
//
///**
// * 插件路由处理器
// * @author zhengyang
// * @className RoutingAwareBeanPostProcessor
// * @date 2021/6/24 15:07
// **/
public class RoutingAwareBeanPostProcessor  {
//
//    private ApplicationContext applicationContext;
//
//    private static final List<String> excludeClass = List.of(MBeanServer.class.getName());
//
//    @Override
//    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
//        if(ReflectUtils.hasInterface(bean.getClass(), excludeClass)) {
//            return true;
//        }
//        ReflectUtils.doWithFields(bean.getClass(),field -> {
//            ReflectUtils.makeAccessible(field);
//            String cacheName = field.getType().getName();
//            Object obj = ServiceProxyCaching.getService(cacheName, bean.getClass());
//            if(Objects.isNull(obj)){
//                obj = Proxy.newProxyInstance(this.getClass().getClassLoader()
//                        ,new Class[] { field.getType()},
//                        new RoutingProxyHandler<>(applicationContext,field.getType()));
//                ServiceProxyCaching.setService(cacheName, obj);
//            }
//            ReflectUtils.setFieldVal(bean,field, obj);
//        },field -> {
//            ReflectUtils.makeAccessible(field);
//            return Objects.nonNull(field.getAnnotation(RoutingResource.class));
//        });
//        return true;
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
}
