package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PayOrderDetailResponse
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/9/17 14:19
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxRefundOrderDetailReponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "返回状态码")
    private String return_code;                      //返回状态码

    @Schema(description = "返回信息")
    private String return_msg;                      //返回信息

    @Schema(description = "业务结果")
    private String result_code;                      //业务结果


    @Schema(description = "错误代码")
    private String err_code;                         //错误代码

    @Schema(description = "错误代码描述")
    private String err_code_des;                     //错误代码描述


    @Schema(description = "公众账号ID")
    private String appid;                            //公众账号ID

    @Schema(description = "商户号")
    private String mch_id;                           //商户号

    @Schema(description = "随机字符串")
    private String nonce_str;                        //随机字符串

    @Schema(description = "签名")
    private String sign;                             //签名

    @Schema(description = "微信支付订单号")
    private String transaction_id;                   //微信支付订单号


    @Schema(description = "商户订单号")
    private String out_trade_no;                     //商户订单号

    @Schema(description = "订单总退款次数")
    private Integer total_refund_count;             //订单总退款次数

    @Schema(description = "订单总金额")
    private String total_fee;                        //订单总金额

    @Schema(description = "货币种类")
    private String fee_type;                         //货币种类


    @Schema(description = "现金支付金额")
    private String cash_fee;                         //现金支付金额


    @Schema(description = "现金支付货币类型")
    private String cash_fee_type;                    //现金支付货币类型

    @Schema(description = "应结订单金额")
    private String settlement_total_fee;             //应结订单金额

    @Schema(description = "退款笔数")
    private Integer refund_count;             //退款笔数

    @Schema(description = "商户退款单号")
    private String out_refund_no_0;             //商户退款单号

    @Schema(description = "微信退款单号")
    private String refund_id_0;             //微信退款单号

    @Schema(description = "退款渠道")
    private String refund_channel_0;             //退款渠道


    @Schema(description = "退款金额")
    private Integer refund_fee_0;             //退款金额


    @Schema(description = "退款状态")
    private String refund_status_0;             //退款状态


    @Schema(description = "退款资金来源")
    private String refund_account_0;             //退款资金来源

    @Schema(description = "退款入账账户")
    private String refund_recv_accout_0;             //退款入账账户

    @Schema(description = "退款成功时间")
    private String refund_success_time_0;             //退款成功时间


}
