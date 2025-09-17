package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 微信支付退款接口参数
 */
@Schema
@Data
public class WxPayRefundRequest {

    private static final long serialVersionUID = 8608617872604487533L;
    /**
     * 必传参数
     */
    @Schema(description = "公众账号ID")
    private String appid;                           //公众账号ID

    @Schema(description = "商户号")
    private String mch_id;                          //商户号

    @Schema(description = "随机字符串")
    private String nonce_str;                       //随机字符串

    @Schema(description = "签名")
    private String sign;                            //签名

    @Schema(description = "微信订单号以及商户订单号（二选一）")
    private String transaction_id;                  //微信订单号以及商户订单号（二选一）

    @Schema(description = "商户订单号")
    private String out_trade_no;                    //商户订单号

    @Schema(description = "商户退款单号")
    private String out_refund_no;                   //商户退款单号

    @Schema(description = "订单金额")
    private String total_fee;                       //订单金额

    @Schema(description = "退款金额")
    private String refund_fee;                      //退款金额

    @Schema(description = "退款结果通知url")
    private String notify_url;                      //退款结果通知url

    /**
     * 非必传参数
     */
    @Schema(description = "签名类型")
    private String sign_type;                        //签名类型

    @Schema(description = "退款货币种类")
    private String refund_fee_type;                  //退款货币种类

    @Schema(description = "退款原因")
    private String refund_desc;                      //退款原因

    @Schema(description = "退款资金来源")
    private String refund_account;                   //退款资金来源

    @Schema(description = "支付类型")
    private String payType;                   //退款支付类型

    @Schema(description = "店铺id")
    private Long storeId;                   //退款店铺id

    @Schema(description = "对应退款的支付方式")
    private String pay_type;                        //对应退款的支付方式--APP:APP支付微信支付类型--为app，对应调用参数对应开放平台参数； "PC/H5/JSAPI";微信支付类型--为PC/H5/JSAPI，对应调用参数对应公众平台参数

    @Schema(description = "退款类型")
    private String refund_type;                     //退款类型：REPEATPAY：重复支付

}
