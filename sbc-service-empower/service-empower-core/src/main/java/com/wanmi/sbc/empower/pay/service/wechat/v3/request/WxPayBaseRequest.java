package com.wanmi.sbc.empower.pay.service.wechat.v3.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
*
 * @description  微信支付下单基础请求参数
 * @author  wur
 * @date: 2022/11/28 13:50
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WxPayBaseRequest implements Serializable {

    private static final long serialVersionUID = 7736763604536062258L;
    /**
     * 应用Id
     */
    private String appid;

    /**
     * 商户号
     */
    private String mchid;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 通知地址
     */
    private String notify_url;

    /**
     * 支付金额
     */
    private WxPayAmount amount;

    /**=============  非必填项  ================*/
    /**
     * 交易结束时间    非必填
     */
    private String time_expire;

    /**
     * 附加信息   非必填
     */
    private String attach;

    /**
     * 订单优惠标记  非必填
     */
    private String goods_tag;

}
