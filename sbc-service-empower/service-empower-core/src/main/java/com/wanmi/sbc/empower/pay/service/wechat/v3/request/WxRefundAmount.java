package com.wanmi.sbc.empower.pay.service.wechat.v3.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信支付--统一下单请求参数--native扫码支付
 * @author Y
 *
 */
@Data
public class WxRefundAmount implements Serializable {

    private static final long serialVersionUID = 7736763604536062258L;
    /**
     * 退款金额
     */
    private Integer refund;

    /**
     * 原订单总额
     */
    private Integer total;

    /**
     * 货币类型
     */
    private String currency = "CNY";

}
