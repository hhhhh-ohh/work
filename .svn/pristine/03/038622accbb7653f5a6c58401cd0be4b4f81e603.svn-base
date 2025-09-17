package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaRefundResponse
 * @description TODO
 * @date 2022/7/5 21:04
 **/
@Data
@Schema
public class LakalaRefundResponse {
    @Schema(description = "商户号")
    @JSONField(name = "merchant_no")
    private String merchantNo;

    @Schema(description = "商户请求流水号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @Schema(description = "拉卡拉交易流水号")
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

    @Schema(description = "原拉卡拉订单号")
    @JSONField(name = "origin_trade_no")
    private String originTradeNo;

    @Schema(description = "原商户请求流水号")
    @JSONField(name = "origin_out_trade_no")
    private String originOutTradeNo;

    @Schema(description = "单品营销 附加数据")
    @JSONField(name = "up_iss_addn_data")
    private String upIssAddnData;

    @Schema(description = "银联优惠信息、出资方信息")
    @JSONField(name = "up_coupon_info")
    private String upCouponInfo;

    @Schema(description = "出资方信息")
    @JSONField(name = "trade_info")
    private String tradeInfo;
}
