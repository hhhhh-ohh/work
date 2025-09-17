package com.wanmi.sbc.goods.plugin;

import com.wanmi.sbc.common.plugin.annotation.BindingPlugin;
import com.wanmi.sbc.common.plugin.annotation.PluginMethod;
import org.springframework.stereotype.Service;

/**
 * @author zhanggaolei
 * @className PluginTestService
 * @description
 * @date 2022/10/19 17:26
 **/
@BindingPlugin
@Service
public class PluginTestService2 {

    @PluginMethod(value = "com.wanmi.sbc.goods.plugin.PluginTestService.test",order = 2)
    public String test(String param){
        System.out.println("service2");
        return "service2";
    }
}
