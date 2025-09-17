package com.wanmi.sbc.empower.pay.service;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import com.wanmi.sbc.empower.pay.repository.GatewayConfigRepository;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class PayServiceFactory {

    //自动注入到map
    @Autowired
    private Map<String,PayBaseService> payBaseServiceMap;

    @Autowired
    private GatewayConfigRepository gatewayConfigRepository;

    public PayBaseService create(PayType payType){
        // 如果是微信支付  验证V2的ApiKey是否有配置，如果没配置则使用V3版本 为了兼容系统老数据 V2优先级高于V3
        if (Objects.equals(PayType.WXPAY, payType)) {
            PayGatewayConfig payGatewayConfig = gatewayConfigRepository.queryConfigByNameAndStoreId(PayGatewayEnum.WECHAT, Constants.BOSS_DEFAULT_STORE_ID);
            if (StringUtils.isBlank(payGatewayConfig.getApiKey())) {
                payType = PayType.WXV3PAY;
            }
        }
        return payBaseServiceMap.get(payType.getPayService());
    }
}
