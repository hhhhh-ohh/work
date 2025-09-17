package com.wanmi.sbc.pay.service.impl;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForAppRequest;
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
 * @type WechatAppPayService.java
 * @desc
 * @date 2022/11/17 15:47
 */
@PayPluginService(type = PayChannelType.WX_APP)
public class WechatAppPayService extends ExternalPayService<WxPayForAppRequest>{

    @Override
    protected void check(PayChannelRequest request) {
        //验证H5支付开关
        DefaultFlag wxOpenFlag = wechatSetService.getStatus(com.wanmi.sbc.common.enums.TerminalType.APP);
        if (DefaultFlag.NO.equals(wxOpenFlag)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
    }

    @Override
    protected WxPayForAppRequest buildRequest(PayBaseBean bean, PayChannelRequest request) {
        WxPayForAppRequest buildRequest = new WxPayForAppRequest();
        buildRequest.setBody(bean.getBody() + "订单");
        buildRequest.setOut_trade_no(bean.getPayNo());
        buildRequest.setTotal_fee(bean.getTotalPrice()
                .multiply(new BigDecimal(Constants.NUM_100))
                .setScale(0, RoundingMode.DOWN).toString());
        buildRequest.setSpbill_create_ip(HttpUtil.getIpAddr());
        buildRequest.setTrade_type(WxPayTradeType.APP.toString());
        buildRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        return buildRequest;
    }

    @Override
    protected BasePayRequest buildPayRequest(WxPayForAppRequest t,PayBaseBean bean) {
        return BasePayRequest.builder()
                .payType(PayType.WXPAY)
                .wxPayType(Constants.FOUR)
                .wxPayForAppRequest(t)
                .tradeId(bean.getId())
                .build();
    }

}
