package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信支付统一下单返回参数--微信公众号支付
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPayForJSApiResponse implements Serializable {

    private static final long serialVersionUID = -1863425421115873101L;
    @Schema(description = "返回状态码")
    private String return_code;             //返回状态码

    @Schema(description = "返回信息")
    private String return_msg;              //返回信息

    @Schema(description = "公众账号ID")
    private String appid;                   //公众账号ID

    @Schema(description = "商户号")
    private String mch_id;                  //商户号

    @Schema(description = "设备号")
    private String device_info;             //设备号

    @Schema(description = "随机字符串")
    private String nonce_str;               //随机字符串

    @Schema(description = "签名")
    private String sign;                    //签名

    @Schema(description = "业务结果")
    private String result_code;             //业务结果

    @Schema(description = "错误代码")
    private String err_code;                //错误代码

    @Schema(description = "错误代码描述")
    private String err_code_des;            //错误代码描述

    @Schema(description = "交易类型")
    private String trade_type;              //交易类型

    @Schema(description = "预支付交易会话标识")
    private String prepay_id;               //预支付交易会话标识
}
