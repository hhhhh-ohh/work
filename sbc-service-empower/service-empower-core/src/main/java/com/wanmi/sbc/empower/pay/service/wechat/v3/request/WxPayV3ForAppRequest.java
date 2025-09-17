package com.wanmi.sbc.empower.pay.service.wechat.v3.request;

import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForAppRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayForNativeRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @description  微信扫码支付下单请求
 * @author  wur
 * @date: 2022/11/28 13:50
 **/
@NoArgsConstructor
@Data
public class WxPayV3ForAppRequest extends WxPayBaseRequest {

    private static final long serialVersionUID = 7736763604536062258L;

    public WxPayV3ForAppRequest(WxPayForAppRequest request) {
        this.setAppid(request.getAppid());
        this.setDescription(request.getBody());
        this.setOut_trade_no(request.getOut_trade_no());
        WxPayAmount amount = new WxPayAmount();
        amount.setTotal(Integer.valueOf(request.getTotal_fee()));
        this.setAmount(amount);
        this.setTime_expire(request.getTime_expire());
    }

}
