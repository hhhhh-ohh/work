package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaAllPayRequest;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author zhanggaolei
 * @type LakalaAliPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@Slf4j
@PayPluginService(type = PayChannelType.LAKALA_ALI)
public class LakalaAliPayService extends LakalaWechatPayService {



    @Override
    protected LakalaAllPayRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        LakalaAllPayRequest buildRequest = lakalaCommonBuildRequest(bean, request);
        Duration duration = null;
        if (bean.getOrderTimeOut() != null) {
            // 拉卡拉支付订单超时时间最大支持99分钟
            duration = Duration.between(LocalDateTime.now(), bean.getOrderTimeOut());
        }

        buildRequest.setTransType("41");
        buildRequest.setAccountType("ALIPAY");
        LakalaAllPayRequest.AliPayAccBusi aliPayAccBusi =
                new LakalaAllPayRequest.AliPayAccBusi();
        if (duration != null){
            aliPayAccBusi.setTimeout_express(duration.toMinutes() > 99 ? "99" : duration.toMinutes() + "");
        }
        aliPayAccBusi.setQuit_url(request.getSuccessUrl());
        buildRequest.setAccBusiFields(aliPayAccBusi);

        return buildRequest;
    }

}
