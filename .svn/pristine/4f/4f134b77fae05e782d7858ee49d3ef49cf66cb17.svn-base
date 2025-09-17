package com.wanmi.sbc.common.plugin.holder;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.plugin.annotation.Routing;
import com.wanmi.sbc.common.plugin.enums.MethodRoutingRule;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Objects;

/**
 * 持有方法的缓存，初始化时填充，用于减少反射的性能消耗
 * @author zhengyang
 * @className MethodHolder
 * @date 2021/6/26 9:39
 **/
@Data
public final class MethodAnnoAttr {
    public MethodAnnoAttr(Routing routing) {
        this(routing.routingRule(), routing.el(), routing.pluginType());
    }

    public MethodAnnoAttr(MethodRoutingRule routingRule, String el, PluginType pluginType) {
        this.routingRule = routingRule;
        if (StringUtils.isNotEmpty(el)) {
            ExpressionParser parser = new SpelExpressionParser();
            if (!el.startsWith("params")) {
                this.expression = parser.parseExpression("params[0]".concat(el));
            }else{
                this.expression = parser.parseExpression(el);
            }
        }
        this.pluginType = pluginType;
    }

    /***
     * 方法级路由规则
     */
    private MethodRoutingRule routingRule;

    /***
     * 方法持有的EL表达式
     */
    private Expression expression;

    /***
     * 路由的PluginType
     */
    private PluginType pluginType;

    /***
     * 方法执行参数是否匹配缓存的EL表达式
     * @param args          方法执行参数
     * @param className     类名
     * @param methodName    方法名
     * @return
     */
    public boolean matchEl(Object[] args,String className,String methodName){
        if(Objects.isNull(this.expression)){
            return false;
        }
        try {
            EvaluationContext context = new StandardEvaluationContext();
            context.setVariable("params",args);
            return (boolean)expression.getValue(context);
        } catch (ParseException e) {
            throw new IllegalArgumentException(className + "#" + methodName + "el format error!");
        } catch (EvaluationException e) {
            throw new IllegalArgumentException(className + "#" + methodName + "el can't match !");
        }
    }
}
