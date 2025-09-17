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
 * @type LakalaUnionPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@Slf4j
@PayPluginService(type = PayChannelType.LAKALA_UNION)
public class LakalaUnionPayService extends LakalaWechatPayService {



    @Override
    protected LakalaAllPayRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        LakalaAllPayRequest buildRequest = lakalaCommonBuildRequest(bean, request);
        Duration duration = null;
        if (bean.getOrderTimeOut() != null) {
            // 拉卡拉支付订单超时时间最大支持99分钟
            duration = Duration.between(LocalDateTime.now(), bean.getOrderTimeOut());
        }

        buildRequest.setTransType("41");
        buildRequest.setAccountType("UQRCODEPAY");
        LakalaAllPayRequest.uqrCodePayAccBusi uqrCodePayAccBusi =
                new LakalaAllPayRequest.uqrCodePayAccBusi();
        uqrCodePayAccBusi.setUser_id(request.getCode());
        if (duration != null) {
            uqrCodePayAccBusi.setTimeout_express(duration.toMinutes() + "");
        }
        uqrCodePayAccBusi.setFront_url(request.getSuccessUrl());

        return buildRequest;
    }

}
