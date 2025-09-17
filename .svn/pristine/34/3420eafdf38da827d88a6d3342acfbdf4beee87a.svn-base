package com.wanmi.sbc.common.plugin.handler;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.PluginRouting;
import com.wanmi.sbc.common.plugin.enums.MethodRoutingRule;
import com.wanmi.sbc.common.plugin.enums.PluginRoutingRule;
import com.wanmi.sbc.common.plugin.holder.MethodAnnoAttr;
import com.wanmi.sbc.common.plugin.holder.ServiceHolder;
import com.wanmi.sbc.common.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 路由插件代理处理器
 * @author zhengyang
 * @className RoutingProxyHandler
 * @date 2021/6/24 15:11
 **/
@Slf4j
public class RoutingProxyHandler<T> implements InvocationHandler {

    /***
     * 支持的Class
     */
    private Class<T> holderCls;

    /***
     * 方法级的实现类
     */
    private List<ServiceHolder<T>> methodList = new ArrayList<>(8);

    /***
     * 主类
     */
    private ServiceHolder<T> mainHandler;

    /***
     * 替换类
     */
    private ServiceHolder<T> replaceHandler;

    private ApplicationContext context;

    public RoutingProxyHandler(ApplicationContext context, Class<T> holderCls) {
        this.context = context;
        this.holderCls = holderCls;
        this.mainHandler = null;
        this.replaceHandler = null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 类级替换优先级最高
        try {
            if (Objects.nonNull(replaceHandler)) {
                return method.invoke(replaceHandler.getService(),args);
            }
            // 方法级替换优先级其次
            if (Objects.nonNull(methodList)) {
                for (ServiceHolder<T> bean : methodList) {
                    MethodAnnoAttr methodAnnoAttr = bean.getMethodAnnoAttr(method.getName());
                    if (Objects.isNull(methodAnnoAttr)) {
                        continue;
                    }
                    if(methodAnnoAttr.getRoutingRule() == MethodRoutingRule.REPLACE){
                        return method.invoke(bean.getService(),args);
                    }
                    if (methodAnnoAttr.getRoutingRule() == MethodRoutingRule.EL) {
                        if (methodAnnoAttr.matchEl(args, bean.getService().getClass().getName(), method.getName())) {
                            return method.invoke(bean.getService(), args);
                        }
                    }
                    // 与业务绑定的规则，根据参数中的Plugin_Type确认路由
                    if (methodAnnoAttr.getRoutingRule() == MethodRoutingRule.PLUGIN_TYPE){
                        Field field = null;
                        for (Object arg : args) {
                            if(Objects.nonNull(arg)){
                                if(PluginType.class.equals(arg.getClass())
                                        && methodAnnoAttr.getPluginType().equals((PluginType)arg)){
                                    return method.invoke(bean.getService(), args);
                                }
                                field = ReflectUtils.findField(arg.getClass(),"pluginType");
                                if(Objects.nonNull(field)){
                                    ReflectUtils.makeAccessible(field);
                                    if(methodAnnoAttr.getPluginType() == field.get(arg)){
                                        return method.invoke(bean.getService(), args);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 主流程优先级最低
            if(Objects.nonNull(mainHandler)){
                return method.invoke(mainHandler.getService(),args);
            }
        } catch (InvocationTargetException e) {
            if(Objects.nonNull(e.getTargetException())){
                throw e.getTargetException();
            }
            throw e;
        } catch (Exception e) {
            throw e;
        }

        log.error("Common Plugin Routing invoke error,can't find match method,"
                        + "class:{},method:{},parms:{}", holderCls.getName(),
                                                        method.getName(), JSON.toJSONString(args));
        return null;
    }

    public void init(){
        // 清理对象，排除干扰
        this.replaceHandler = null;
        this.mainHandler = null;
        this.methodList.clear();

        // 开始初始化
        Map<String,T> allBeansMap = context.getBeansOfType(holderCls);
        // 循环初始化
        for (T bean : allBeansMap.values()) {
            PluginRouting pluginRouting = ReflectUtils.getAnnotation(AopUtils.getTargetClass(bean), PluginRouting.class);

            if (Objects.isNull(pluginRouting)) {
                if (Objects.nonNull(mainHandler)){
                    throw new IllegalStateException("Plugin " + holderCls.getName() + " not support Multiple main Impl!");
                }
                mainHandler = new ServiceHolder(bean);
                continue;
            }

            if(pluginRouting.routingRule() == PluginRoutingRule.REPLACE){
                if (Objects.nonNull(replaceHandler)){
                    throw new IllegalStateException("Plugin " + holderCls.getName() + " not support Multiple replace Impl!");
                }
                replaceHandler = new ServiceHolder(bean);
                continue;
            }

            if(pluginRouting.routingRule() == PluginRoutingRule.METHOD){
                methodList.add(new ServiceHolder(bean, true));
                continue;
            }
        }
    }
}
