package com.wanmi.sbc.common.plugin.enums;

/**
 * 方法路由规则
 * @author zhengyang
 * @className MethodRoutingRule
 * @date 2021/6/24 15:25
 **/
public enum MethodRoutingRule {

    /***
     * 整体替换
     */
    REPLACE,

    /***
     * EL处理
     */
    EL,

    /***
     * 根据参数中的PluginType确定
     */
    PLUGIN_TYPE;
}
