package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForJSApiRequest;
import com.wanmi.sbc.empower.bean.enums.PayType;
import com.wanmi.sbc.empower.bean.enums.WxPayTradeType;
import com.wanmi.sbc.pay.bean.PayBaseBean;
import com.wanmi.sbc.pay.config.PayPluginService;
import com.wanmi.sbc.pay.request.PayChannelRequest;
import com.wanmi.sbc.pay.request.PayChannelType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhanggaolei
 * @type WechatH5PayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@PayPluginService(type = PayChannelType.WX_H5)
public class WechatH5PayService extends ExternalPayService<WxPayForJSApiRequest> {
    @Override
    protected WxPayForJSApiRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        WxPayForJSApiRequest buildRequest = new WxPayForJSApiRequest();
        buildRequest.setBody(bean.getBody() + "订单");
        buildRequest.setOut_trade_no(bean.getPayNo());
        buildRequest.setTotal_fee(
                bean.getTotalPrice()
                        .multiply(new BigDecimal(Constants.NUM_100))
                        .setScale(0, RoundingMode.DOWN)
                        .toString());
        buildRequest.setSpbill_create_ip(HttpUtil.getIpAddr());
        buildRequest.setTrade_type(WxPayTradeType.JSAPI.toString());
        buildRequest.setOpenid(bean.getOpenId());
        buildRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        return buildRequest;
    }

    @Override
    protected BasePayRequest buildPayRequest(WxPayForJSApiRequest t,PayBaseBean bean) {
        return BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.TWO)
                .wxPayForJSApiRequest(t)
                .tradeId(bean.getId())
                .build();
    }
}
