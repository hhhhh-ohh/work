package com.wanmi.sbc.common.configure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhanggaolei
 * @className ApplicationContextConfigure
 * @description TODO
 * @date 2021/4/16 13:45
 **/
@Component
public class ApplicationContextConfigure implements ApplicationContextAware {

    public static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(CONTEXT == null){
            CONTEXT =  applicationContext;
        }
    }

    public static synchronized void register(String name,Class<?> beanClass){
        if (!CONTEXT.containsBean(name)) {
            register(name, beanClass, null);
        }
    }

    public static void register(String name, Class<?> beanClass, Map<String,Object> varMap){
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) CONTEXT
                .getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        if(varMap!=null){
            for (Map.Entry<String,Object> entry : varMap.entrySet()) {
                beanDefinitionBuilder.addPropertyValue(entry.getKey(),entry.getValue());
            }
        }
        beanFactory.registerBeanDefinition(name, beanDefinitionBuilder.getBeanDefinition());
    }
}
