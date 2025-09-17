package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信支付--统一下单请求参数--native扫码支付
 * @author Y
 *
 */
@Schema
@Data
public class WxPayForJSApiBaseRequest implements Serializable {

    private static final long serialVersionUID = 5637049059065668613L;

    /**---必填项---**/
    @Schema(description = "公众账号ID")
    private String appid;               //公众账号ID

    @Schema(description = "商户号")
    private String mch_id;              //商户号

    @Schema(description = "随机字符串")
    private String nonce_str;           //随机字符串

    @Schema(description = "签名")
    private String sign;                //签名

    @Schema(description = "商品描述")
    private String body;                //商品描述

    @Schema(description = "商户订单号")
    private String out_trade_no;        //商户订单号

    @Schema(description = "总金额")
    private String total_fee;           //总金额

    @Schema(description = "终端IP")
    private String spbill_create_ip;    //终端IP

    @Schema(description = "通知地址")
    private String notify_url;          //通知地址

    @Schema(description = "交易类型")
    private String trade_type;          //交易类型

    @Schema(description = "商品ID")
    private String product_id;          //商品ID--扫码支付Native

    /**---非必填项---**/
    @Schema(description = "设备号")
    private String device_info;         //设备号

    @Schema(description = "签名类型")
    private String sign_type;           //签名类型

    @Schema(description = "商品详情")
    private String detail;              //商品详情

    @Schema(description = "附加数据")
    private String attach;              //附加数据

    @Schema(description = "货币类型")
    private String fee_type;            //货币类型

    @Schema(description = "交易起始时间")
    private String time_start;          //交易起始时间

    @Schema(description = "交易结束时间")
    private String time_expire;         //交易结束时间

    @Schema(description = "商品标记")
    private String goods_tag;           //商品标记

    @Schema(description = "指定支付方式")
    private String limit_pay;           //指定支付方式

    @Schema(description = "用户标识")
    private String openid;              //用户标识

    @Schema(description = "电子发票入口开放标识")
    private String receipt;             //电子发票入口开放标识

    @Schema(description = "场景信息")
    private String scene_info;          //场景信息



}
