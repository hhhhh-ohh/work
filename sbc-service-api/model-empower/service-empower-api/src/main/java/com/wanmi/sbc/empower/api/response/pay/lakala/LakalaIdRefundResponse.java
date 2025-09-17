package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaIdRefundResponse
 * @description TODO
 * @date 2022/7/5 22:10
 **/
@Data
@Schema
public class LakalaIdRefundResponse {
    @Schema(description = "商户号")
    @JSONField(name = "merchant_no")
    private String merchantNo;

    @Schema(description = "商户退款订单号")
    @JSONField(name = "out_refund_order_no")
    private String outRefundOrderNo;

    @Schema(description = "商户请求流水号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @Schema(description = "拉卡拉退款单号")
    @JSONField(name = "trade_no")
    private String tradeNo;

    @Schema(description = "拉卡拉对账单流水号")
    @JSONField(name = "log_no")
    private String logNo;

    @Schema(description = "账户端交易订单号")
    @JSONField(name = "acc_trade_no")
    private String accTradeNo;

    @Schema(description = "钱包类型 微信：WECHAT 支付宝：ALIPAY 银联：UQRCODEPAY 翼支付: BESTPAY 苏宁易付宝: SUNING")
    @JSONField(name = "account_type")
    private String accountType;

    @Schema(description = "交易金额")
    @JSONField(name = "total_amount")
    private String totalAmount;

    @Schema(description = "申请退款金额")
    @JSONField(name = "refund_amount")
    private String refundAmount;

    @Schema(description = "实际退款金额")
    @JSONField(name = "payer_amount")
    private String payerAmount;

    @Schema(description = "退款时间")
    @JSONField(name = "trade_time")
    private String tradeTime;

}
