package com.wanmi.sbc.pay.config;

import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.pay.request.PayChannelType;

import java.lang.annotation.*;

/**
 * @author zhanggaolei
 * @className PayPluginService
 * @description
 * @date 2022/11/16 13:42
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PayPluginService {
    
    PayChannelType type() default PayChannelType.WX_H5;
}
