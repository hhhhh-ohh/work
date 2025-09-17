package com.wanmi.sbc.common.configure;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.util.FileResourceUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;

/** 万米资源注入 */
@Component
public class WmResourceProcess implements BeanPostProcessor {

    /** 要在创建bean之前 */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        // 使用反射给类的属性赋值
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            WmResource annotation = declaredField.getAnnotation(WmResource.class);
            if (null == annotation) {
                continue;
            }
            // 去掉private 私有访问权限
            declaredField.setAccessible(true);
            try {
                declaredField.set(
                        bean,
                        FileResourceUtil.getPackageInsideResourcesByPattern(annotation.value()));
            } catch (IllegalAccessException | IOException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
