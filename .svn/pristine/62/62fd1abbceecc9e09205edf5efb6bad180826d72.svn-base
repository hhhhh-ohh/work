package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForJSApiRequest;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;

/**
 * @author zhanggaolei
 * @type WechatMiniProgramPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@PayPluginService(type = PayChannelType.WX_MINI_PROGRAM)
public class WechatMiniProgramPayService extends WechatH5PayService {

    @Override
    protected void check(PayChannelRequest request) {

    }

    @Override
    protected BasePayRequest buildPayRequest(WxPayForJSApiRequest t, PayBaseBean bean) {
        return BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.THREE)
                .wxPayForJSApiRequest(t)
                .tradeId(bean.getId())
                .build();
    }
}
