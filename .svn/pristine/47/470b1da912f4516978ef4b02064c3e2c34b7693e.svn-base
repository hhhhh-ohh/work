package com.wanmi.sbc.common.plugin.handler;

import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 初始化所有代理方法
 * @author zhengyang
 * @className RoutingInitHandler
 * @date 2021/6/25 10:11
 **/
@Slf4j
//@Component
public class RoutingDefaultBehaviorHandler implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /***
     * 例外列表
     */
    private static final List IGNORE_LIST = new ArrayList();
    static{
        IGNORE_LIST.add("org.springframework.cloud.openfeign.FeignClientFactoryBean");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!applicationContext.containsBean(RoutingInitHandler.class.getName())) {
            // 获得容器所有的Bean
            String[] beanNames = applicationContext.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                // 跳过所有Spring类
                if (beanName.startsWith("org.spring")) {
                    continue;
                }
                // 容器中不存在跳过
                if(!applicationContext.containsBean(beanName)){
                    continue;
                }

                // 判断是否在例外中，如果在直接跳出
                if (ignoreBean(beanName)){
                    continue;
                }

                // 取得对象
                Object install = null;
                try {
                    install = applicationContext.getBean(beanName);
                } catch (Throwable e) {
                    log.error("RoutingDefaultBehaviorHandler getBean error,beanName is {} ,error=>",beanName,e);
                }
                if (Objects.nonNull(install)){
                    try {
                        Class<?> clz = install.getClass();

                        // 循环赋值
                        Object finalInstall = install;
                        ReflectUtils.doWithFields(clz, field -> {
                            // 从Spring上下文中获得对象并赋值
                            if (Objects.isNull(field.get(finalInstall))) {
                                Map<String, ?> installMap = applicationContext.getBeansOfType(field.getType());
                                if (!installMap.isEmpty()) {
                                    ReflectUtils.setFieldVal(finalInstall, field, installMap.values().stream().findFirst().get());
                                }
                            }
                        }, field -> {
                            ReflectUtils.makeAccessible(field);
                            return Objects.nonNull(field.getAnnotation(RoutingResource.class));
                        });
                    }catch (Exception e){
                        log.error("RoutingDefaultBehaviorHandler setFieldVal error,beanName is {} ,error=>",getAllBeanName(beanName),e);
                    }
                }
            }
        }
    }

    /***
     * 判断一个类是否需要忽略
     * @param beanName  Bean名称
     * @return          是否需要忽略
     */
    private boolean ignoreBean(String beanName){
        if(applicationContext instanceof GenericApplicationContext){
            GenericApplicationContext beanContext = (GenericApplicationContext) applicationContext;
            BeanDefinition beanDefinition = beanContext.getBeanDefinition(beanName);
            // 取不到Bean定义或者Bean定义中类名为空属于特殊类，不扫描直接跳过
            if (Objects.isNull(beanDefinition)
                    || Objects.isNull(beanDefinition.getBeanClassName())) {
                return true;
            }
            if(beanDefinition.getBeanClassName().startsWith("com.wanmi.sbc.job.scantask")) {
                return true;
            }
            return IGNORE_LIST.contains(beanDefinition.getBeanClassName())
                    || !beanDefinition.getBeanClassName().startsWith("com.wanmi");
        }
        return false;
    }

    private String getAllBeanName(String beanName) {
        if(applicationContext instanceof GenericApplicationContext){
            GenericApplicationContext beanContext = (GenericApplicationContext) applicationContext;
            BeanDefinition beanDefinition = beanContext.getBeanDefinition(beanName);
            // 取不到Bean定义或者Bean定义中类名为空属于特殊类，不扫描直接跳过
            if (Objects.isNull(beanDefinition)
                    || Objects.isNull(beanDefinition.getBeanClassName())) {
                return beanName;
            }
            return beanDefinition.getBeanClassName();
        }
        return beanName;
    }
}
