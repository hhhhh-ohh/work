package com.wanmi.sbc.order.paycallback;

import com.wanmi.sbc.order.bean.enums.PayAndRefundCallBackServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PayAndRefundCallBackServiceFactory {
    //自动注入到map
    @Autowired
    private Map<String,PayAndRefundCallBackBaseService> payCallBackBaseServiceMap;

    public PayAndRefundCallBackBaseService create(PayAndRefundCallBackServiceType payCallBackServiceType){
        return payCallBackBaseServiceMap.get(payCallBackServiceType.getPayCallBackService());
    }
}
