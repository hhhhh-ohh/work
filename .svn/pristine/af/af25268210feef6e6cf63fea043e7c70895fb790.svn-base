package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelType;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;

/**
 * @author zhanggaolei
 * @type AliAppPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@Slf4j
@PayPluginService(type = PayChannelType.ALI_APP)
public class AliAppPayService extends AliH5PayService {


    /**
     * 放在此处处理防止被父类的方法篡改
     * @param baseResponse
     * @return
     */
    @Override
    protected BaseResponse resultProcess(BaseResponse baseResponse) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) baseResponse.getContext();
        String form = String.valueOf(linkedHashMap.get("form"));
        return BaseResponse.success(form);
    }
}
