package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信支付企业支付到零钱接口返回参数
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPayCompanyPaymentRsponse implements Serializable {

    private static final long serialVersionUID = -6457221265995114890L;
    @Schema(description = "返回状态码")
    private String return_code;                      //返回状态码：SUCCESS/FAIL；此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断

    @Schema(description = "返回信息")
    private String return_msg;                       //返回信息：返回信息，如非空，为错误原因 签名失败 参数格式校验错误

    @Schema(description = "商户appid")
    private String mch_appid;                        //商户appid

    @Schema(description = "商户号")
    private String mchid;                           //商户号

    @Schema(description = "设备号")
    private String device_info;                      //设备号

    @Schema(description = "随机字符串")
    private String nonce_str;                        //随机字符串

    @Schema(description = "业务结果")
    private String result_code;                      //业务结果

    @Schema(description = "错误代码")
    private String err_code;                         //错误代码

    @Schema(description = "错误代码描述")
    private String err_code_des;                     //错误代码描述

    @Schema(description = "商户订单号")
    private String partner_trade_no;                 //商户订单号

    @Schema(description = "微信付款单号")
    private String payment_no;                       //微信付款单号

    @Schema(description = "付款成功时间")
    private String payment_time;                     //付款成功时间，企业付款成功时间

}
