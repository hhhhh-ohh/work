package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaNotifyRequest
 * @description TODO
 * @date 2022/7/5 21:47
 **/
@Data
@Schema
public class LakalaNotifyRequest {
    @Schema(description = "商户交易流水号")
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

    @Schema(description = "结算商户号")
    @JSONField(name = "settle_merchant_no")
    private String settleMerchantNo;

    @Schema(description = "结算终端号")
    @JSONField(name = "settle_term_no")
    private String settleTermNo;

    @Schema(description = "交易状态 INIT-初始化 CREATE-下单成功 SUCCESS-交易成功 FAIL-交易失败 DEAL-交易处理中 UNKNOWN-未知状态 CLOSE-订单关闭 PART_REFUND-部分退款 REFUND-全部退款 REVOKED-订单撤销")
    @JSONField(name = "trade_status")
    private String tradeStatus;

    @Schema(description = "订单金额 单位分，整数数字型字符")
    @JSONField(name = "total_amount")
    private String totalAmount;

    @Schema(description = "付款人实付金额 付款人实付金额，单位分")
    @JSONField(name = "payer_amount")
    private String payerAmount;

    @Schema(description = "账户端结算金额 账户端应结订单金额，单位分 ，账户端应结订单金额=付款人实际发生金额+账户端优惠金额")
    @JSONField(name = "acc_settle_amount")
    private String accSettleAmount;

    @Schema(description = "交易完成时间 实际支付时间。yyyyMMddHHmmss")
    @JSONField(name = "trade_time")
    private String tradeTime;

    @Schema(description = "用户标识1")
    @JSONField(name = "user_id1")
    private String userId1;

    @Schema(description = "用户标识2")
    @JSONField(name = "user_id2")
    private String userId2;

    @Schema(description = "付款银行")
    @JSONField(name = "bank_type")
    private String bankType;

    @Schema(description = "银行卡类型 00：借记 01：贷记 02：微信零钱 03：支付宝花呗 04：支付宝其他 05：数字货币 06：拉卡拉支付账户 99：未知 sha")
    @JSONField(name = "card_type")
    private String cardType;

    @Schema(description = "备注")
    private String remark;

}
