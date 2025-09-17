package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
*
 * @description   WxChannelsPayRequest   微信视频号支付请求
 * @author  wur
 * @date: 2022/4/7 17:42
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class WxChannelsPayRequest implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义订单ID，必填
     */
    @Schema(description = "商家自定义订单ID，必填")
    private String orderId;

    /**
     * 微信侧订单ID 从下单接口的返回中获取  必填
     */
    @Schema(description = "微信侧订单ID 从下单接口的返回中获取  必填")
    private String thirdOrderId;

    /**
     *用户的openid
     * 必填
     */
    @Schema(description = "用户的openid，必填")
    private String openid;

    /**
     * 订单金额 元单位
     */
    @Schema(description = "订单金额 元单位")
    private String totalPrice;

    @Schema(description = "用户IP")
    private String clientIp;
}
