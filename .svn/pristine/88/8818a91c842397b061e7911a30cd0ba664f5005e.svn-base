package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信退款回调数据
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPayRefundCallBackDataResponse implements Serializable {

    private static final long serialVersionUID = 6627169048337746104L;
    @Schema(description = "微信订单号")
    private String transaction_id;      //微信订单号

    @Schema(description = "商户订单号")
    private String out_trade_no;        //商户订单号

    @Schema(description = "微信退款单号")
    private String refund_id;           //微信退款单号

    @Schema(description = "商户退款单号")
    private String out_refund_no;       //商户退款单号

    @Schema(description = "订单金额")
    private String total_fee;           //订单金额

    @Schema(description = "应结订单金额")
    private String settlement_total_fee;    //应结订单金额

    @Schema(description = "申请退款金额")
    private String refund_fee;          //申请退款金额

    @Schema(description = "退款金额")
    private String settlement_refund_fee; //退款金额

    @Schema(description = "退款状态")
    private String refund_status;       //退款状态

    @Schema(description = "退款成功时间")
    private String success_time;        //退款成功时间

    @Schema(description = "退款入账账户")
    private String refund_recv_accout;  //退款入账账户

    @Schema(description = "退款资金来源")
    private String refund_account;      //退款资金来源

    @Schema(description = "退款发起来源")
    private String refund_request_source;//退款发起来源

}
