package com.wanmi.sbc.empower.api.response.pay.weixin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
*
 * @description   生成订单支付参数
 * @author  wur
 * @date: 2022/4/12 14:40
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxChannelsPayParamResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 统一下单接口返回的 prepay_id 参数值
     */
    private String payPackage;

    /**
     * 签名
     */
    private String paySign;

    /**
     * 签名算法
     */
    private String signType;

    /**
     * nonceStr
     */
    private String nonceStr;

}
