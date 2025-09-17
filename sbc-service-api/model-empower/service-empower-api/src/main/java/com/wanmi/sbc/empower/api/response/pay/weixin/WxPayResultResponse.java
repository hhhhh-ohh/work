package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信支付回调参数据
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPayResultResponse implements Serializable {

    private static final long serialVersionUID = 3190306218606889536L;
    @Schema(description = "公众账号ID")
    private String appid;                            //公众账号ID

    @Schema(description = "商户号")
    private String mch_id;                           //商户号

    @Schema(description = "设备号")
    private String device_info;                      //设备号

    @Schema(description = "随机字符串")
    private String nonce_str;                        //随机字符串

    @Schema(description = "签名")
    private String sign;                             //签名

    @Schema(description = "签名类型")
    private String sign_type;                        //签名类型

    @Schema(description = "返回状态码")
    private String return_code;                      //返回状态码

    @Schema(description = "业务结果")
    private String result_code;                      //业务结果

    @Schema(description = "错误代码")
    private String err_code;                         //错误代码

    @Schema(description = "错误代码描述")
    private String err_code_des;                     //错误代码描述

    @Schema(description = "用户标识")
    private String openid;                           //用户标识

    @Schema(description = "是否关注公众账号用户是否关注公众账号")
    private String is_subscribe;                     //是否关注公众账号用户是否关注公众账号，Y-关注，N-未关注

    @Schema(description = "交易类型")
    private String trade_type;                       //交易类型

    @Schema(description = "付款银行")
    private String bank_type;                        //付款银行

    @Schema(description = "订单金额")
    private String total_fee;                        //订单金额

    @Schema(description = "应结订单金额")
    private String settlement_total_fee;             //应结订单金额

    @Schema(description = "货币种类")
    private String fee_type;                         //货币种类

    @Schema(description = "现金支付金额")
    private String cash_fee;                         //现金支付金额

    @Schema(description = "现金支付货币类型")
    private String cash_fee_type;                    //现金支付货币类型

    @Schema(description = "总代金券金额")
    private String coupon_fee;                       //总代金券金额

    @Schema(description = "代金券使用数量")
    private String coupon_count;                     //代金券使用数量

    @Schema(description = "代金券类型")
    private String coupon_type_$n;                   //代金券类型

    @Schema(description = "代金券ID")
    private String coupon_id_$n	;                    //代金券ID

    @Schema(description = "单个代金券支付金额")
    private String coupon_fee_$n;                    //单个代金券支付金额

    @Schema(description = "微信支付订单号")
    private String transaction_id;                   //微信支付订单号

    @Schema(description = "商户订单号")
    private String out_trade_no;                     //商户订单号

    @Schema(description = "商家数据包")
    private String attach;                           //商家数据包

    @Schema(description = "支付完成时间支付完成时间")
    private String time_end;                         //支付完成时间支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
}
