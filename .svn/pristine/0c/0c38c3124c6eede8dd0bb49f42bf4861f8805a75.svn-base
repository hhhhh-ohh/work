package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForNativeRequest;
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
 * @type WechatScanPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@PayPluginService(type = PayChannelType.WX_SCAN)
public class WechatScanPayService extends ExternalPayService<WxPayForNativeRequest>{
    @Override
    protected WxPayForNativeRequest buildRequest(PayBaseBean bean,PayChannelRequest request) {
        WxPayForNativeRequest buildRequest = new WxPayForNativeRequest();
        buildRequest.setBody(bean.getBody() + "订单");
        buildRequest.setOut_trade_no(bean.getPayNo());
        buildRequest.setTotal_fee(bean.getTotalPrice()
                .multiply(new BigDecimal(Constants.NUM_100))
                .setScale(0, RoundingMode.DOWN).toString());
        buildRequest.setSpbill_create_ip(HttpUtil.getIpAddr());
        buildRequest.setTrade_type(WxPayTradeType.NATIVE.toString());
        buildRequest.setOpenid(bean.getOpenId());
        buildRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        return buildRequest;
    }

//    @Override
//    protected boolean isPayMember(PayChannelRequest request) {
//        return false;
//    }

    @Override
    protected PayBaseBean payMemberProcess(PayChannelRequest request) {
        throw new SbcRuntimeException(AccountErrorCodeEnum.K020024);
    }

    @Override
    protected BasePayRequest buildPayRequest(WxPayForNativeRequest t,PayBaseBean bean) {
        return BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.ZERO)
                .wxPayForNativeRequest(t)
                .tradeId(bean.getId())
                .build();
    }

}
