package com.wanmi.sbc.empower.sms.service;

import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsSendServiceFactory {

    //自动注入到map
    @Autowired
    private Map<String , SmsSendBaseService> smsSendBaseServiceMap;

    public SmsSendBaseService create(SmsPlatformType smsPlatformType){
        return smsSendBaseServiceMap.get(smsPlatformType.getSmsSendService());
    }
}
