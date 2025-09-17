package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForMWebRequest;
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
 * @type WechatOutH5PayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@PayPluginService(type = PayChannelType.WX_OUT_H5)
public class WechatOutH5PayService extends ExternalPayService<WxPayForMWebRequest>{
    @Override
    protected WxPayForMWebRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        WxPayForMWebRequest buildRequest = new WxPayForMWebRequest();
        buildRequest.setBody(bean.getBody() + "订单");
        buildRequest.setOut_trade_no(bean.getPayNo());
        buildRequest.setTotal_fee(bean.getTotalPrice()
                .multiply(new BigDecimal(Constants.NUM_100))
                .setScale(0, RoundingMode.DOWN).toString());
        buildRequest.setSpbill_create_ip(HttpUtil.getIpAddr());
        buildRequest.setTrade_type(WxPayTradeType.MWEB.toString());
        buildRequest.setScene_info("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://m.s2btest2.kstore.shop\"," +
                "\"wap_name\": \"h5下单支付\"}}");
        buildRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        return buildRequest;
    }

    @Override
    protected BasePayRequest buildPayRequest(WxPayForMWebRequest t,PayBaseBean bean) {
        return BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.ONE)
                .wxPayForMWebRequest(t)
                .tradeId(bean.getId())
                .build();
    }

}
