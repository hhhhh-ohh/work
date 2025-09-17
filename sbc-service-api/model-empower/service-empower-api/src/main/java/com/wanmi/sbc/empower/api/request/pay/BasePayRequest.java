package com.wanmi.sbc.empower.api.request.pay;

import com.wanmi.sbc.empower.api.request.pay.ali.PayExtraRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaAllPayRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaCasherAllPayRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaPayRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.*;
import com.wanmi.sbc.empower.bean.enums.LklPayType;
import com.wanmi.sbc.empower.bean.enums.PayType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasePayRequest {

    /**
     * 银联云闪付 请求参数
     */
    @Schema(description = "银联云闪付 请求参数")
    private UnionPayRequest unionPayRequest;

    /**
     * 支付宝请求参数
     */
    @Schema(description = " 支付宝请求参数")
    private PayExtraRequest payExtraRequest;

    /**
     * 微信native扫码支付请求参数
     */
    @Schema(description = "微信native扫码支付请求参数")
    private WxPayForNativeRequest wxPayForNativeRequest;

    /**
     * 非微信浏览器h5支付请求参数
     */
    @Schema(description = "非微信浏览器h5支付请求参数")
    private WxPayForMWebRequest wxPayForMWebRequest;

    /**
     * 微信浏览器内JSApi支付请求参数或者小程序内JSApi支付请求参数
     */
    @Schema(description = "微信浏览器内JSApi支付请求参数或者小程序内JSApi支付请求参数")
    private WxPayForJSApiRequest wxPayForJSApiRequest;

    /**
     * APP微信支付请求参数
     */
    @Schema(description = "APP微信支付请求参数")
    private WxPayForAppRequest wxPayForAppRequest;


    @Schema(description = "微信视频号支付请求参数")
    private WxChannelsPayRequest wxChannelsPayRequest;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayType payType;

    /**
     * 微信支付方式  0-微信native扫码支付  1-非微信浏览器h5支付 2-微信浏览器内JSApi支付 3-小程序内JSApi支付 4-APP微信支付请求参数
     */
    @Schema(description = "微信支付方式")
    private Integer wxPayType;

    @Schema(description = "拉卡拉聚合支付入参")
    private LakalaAllPayRequest lakalaAllPayRequest;

    @Schema(description = "渠道ID")
    private String channelItemId;

    @Schema(description = "支付单号")
    private String tradeId;

    @Schema(description = "拉卡拉收银台支付入参")
    private LakalaCasherAllPayRequest lakalaCasherAllPayRequest;

    /**
     * 拉卡拉支付方式
     */
    @Schema(description = "拉卡拉支付方式")
    private LklPayType lklPayType;

    private String orderCode;

}
