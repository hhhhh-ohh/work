package com.wanmi.sbc.marketing.newplugin.config;

import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;

import java.lang.annotation.*;

/**
 * @author zhanggaolei
 * @className MarketingPluginService
 * @description
 * @date 2021/5/26 13:42
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MarketingPluginService {
    
    MarketingPluginType type() default MarketingPluginType.OTHER;
}
