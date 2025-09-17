package com.wanmi.sbc.empower.apppush.service;

import com.wanmi.sbc.empower.bean.enums.AppPushPlatformType;
import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;
import com.wanmi.sbc.empower.sms.service.SmsSendBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AppPushServiceFactory {

    //自动注入到map
    @Autowired
    private Map<String , AppPushBaseService> appPushBaseServiceMap;

    public AppPushBaseService create(AppPushPlatformType platformType){
        return appPushBaseServiceMap.get(platformType.getBeanName());
    }
}
