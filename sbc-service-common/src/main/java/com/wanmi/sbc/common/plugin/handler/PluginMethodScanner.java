package com.wanmi.sbc.common.plugin.handler;

import com.wanmi.sbc.common.plugin.annotation.BindingPlugin;
import com.wanmi.sbc.common.plugin.annotation.PluginMethod;
import com.wanmi.sbc.common.plugin.bean.PluginInvoker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className PluginMethodScanner
 * @description TODO
 * @date 2021/8/6 7:52 下午
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "wanmi.plugin", name = "enabled", havingValue = "true", matchIfMissing = false)
public class PluginMethodScanner implements ApplicationListener<ContextRefreshedEvent> {

    protected static final Map<String, List<PluginInvoker>> invokerMap = new HashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        log.info("容器初始化bean数量：{},parent:{}" ,event.getApplicationContext().getBeanDefinitionCount(),event.getApplicationContext().getApplicationName());
        // 根容器为Spring容器
        if (event.getApplicationContext().getDisplayName().contains("AnnotationConfigServletWebServerApplicationContext")) {

            Map<String, Object> beans =
                    event.getApplicationContext().getBeansWithAnnotation(BindingPlugin.class);
            for (Object bean : beans.values()) {

                Method[] methods = bean.getClass().getMethods();
                for (Method declaredMethod : methods) {

                    PluginMethod annotation =
                            AnnotationUtils.findAnnotation(declaredMethod, PluginMethod.class);
                    if (annotation != null) {
                        String value = annotation.value();
                        if (StringUtils.isEmpty(value)) {
                            value = getName(bean.getClass()) + "." + declaredMethod.getName();
                        }
                        if (CollectionUtils.isEmpty(invokerMap.get(value))) {
                            List<PluginInvoker> list = new ArrayList<>();
                            invokerMap.put(value, list);
                        }
                        invokerMap
                                .get(value)
                                .add(
                                        PluginInvoker.builder()
                                                .method(declaredMethod)
                                                .tag(
                                                        getName(bean.getClass())
                                                                + "."
                                                                + declaredMethod.getName())
                                                .bean(bean)
                                                .order(annotation.order())
                                                .replace(annotation.replace())
                                                .build());
                        log.info(
                                "methodName:"
                                        + bean.getClass().getName()
                                        + "."
                                        + declaredMethod.getName());
                    }
                }
            }

            invokerMap.entrySet().stream()
                    .forEach(
                            entry ->
                                    entry.getValue().stream()
                                            .forEach(
                                                    s ->
                                                            log.info(
                                                                    "key:{},value:{}",
                                                                    entry.getKey(),
                                                                    s.toString())));
        }
    }

    private String getName(Class c) {
        return c.getName().substring(0, c.getName().indexOf("$"));
    }

}
