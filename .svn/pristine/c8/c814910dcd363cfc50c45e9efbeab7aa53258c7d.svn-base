package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author edz
 * @className LakalaTradeQueryResponse
 * @description TODO
 * @date 2022/7/5 21:22
 **/
@Data
@Schema
public class LakalaTradeQueryResponse implements Serializable {

    @Schema(description = "原拉卡拉订单号")
    @JSONField(alternateNames = {"origin_trade_no", "originTradeNo"})
    private String originTradeNo;

    @Schema(description = "商户请求流水号")
    @JSONField(alternateNames = {"out_trade_no", "outTradeNo"})
    private String outTradeNo;

    @Schema(description = "拉卡拉商户订单号")
    @JSONField(alternateNames = {"trade_no", "tradeNo"})
    private String tradeNo;

    @Schema(description = "拉卡拉对账单流水号")
    @JSONField(alternateNames = {"log_no", "logNo"})
    private String logNo;

    @Schema(description = "拆单属性 M-主单，S-子单")
    @JSONField(alternateNames = {"split_attr", "splitAttr"})
    private String splitAttr;

    @Schema(description = "拆单信息")
    @JSONField(alternateNames = {"split_info", "splitInfo"})
    private List<SplitInfo> splitInfo;

    @Schema(description = "账户端交易订单号")
    @JSONField(alternateNames = {"acc_trade_no", "accTradeNo"})
    private String accTradeNo;

    @Schema(description = "钱包类型 微信：WECHAT 支付宝：ALIPAY 银联：UQRCODEPAY 翼支付: BESTPAY 苏宁易付宝: SUNING")
    @JSONField(alternateNames = {"account_type", "accountType"})
    private String accountType;

    @Schema(description = "交易状态 INIT-初始化 CREATE-下单成功 SUCCESS-交易成功 FAIL-交易失败 DEAL-交易处理中 UNKNOWN-未知状态 CLOSE-订单关闭 PART_REFUND-部分退款 REFUND-全部退款 REVOKED-订单撤销")
    @JSONField(alternateNames = {"trade_state", "tradeState"})
    private String tradeState;

    @Schema(description = "交易状态描述")
    @JSONField(alternateNames = {"trade_state_desc", "tradeStateDesc"})
    private String tradeStateDesc;

    @Schema(description = "订单金额 单位分，整数数字型字符")
    @JSONField(alternateNames = {"total_amount", "totalAmount"})
    private String totalAmount;

    @Schema(description = "付款人实付金额 付款人实付金额，单位分")
    @JSONField(alternateNames = {"payer_amount", "payerAmount"})
    private String payerAmount;

    @Schema(description = "账户端结算金额 账户端应结订单金额，单位分")
    @JSONField(alternateNames = {"acc_settle_amount", "accSettleAmount"})
    private String accSettleAmount;

    @Schema(description = "商户侧优惠金额 商户优惠金额，单位分")
    @JSONField(alternateNames = {"acc_mdiscount_amount", "accMdiscountAmount"})
    private String accMdiscountAmount;

    @Schema(description = "账户端优惠金额 拉卡拉优惠金额，单位分")
    @JSONField(alternateNames = {"acc_discount_amount", "accDiscountAmount"})
    private String accDiscountAmount;

    @Schema(description = "交易完成时间 实际支付时间。yyyyMMddHHmmss")
    @JSONField(alternateNames = {"trade_time", "tradeTime"})
    private String tradeTime;

    @Schema(description = "微信sub_open_id 支付宝buyer_logon_id（买家支付宝账号）")
    @JSONField(alternateNames = {"user_id1", "userId1"})
    private String userId1;

    @Schema(description = "微信openId支 付宝buyer_user_id 银联user_id")
    @JSONField(alternateNames = {"user_id2", "userId2"})
    private String userId2;

    @Schema(description = "付款银行")
    @JSONField(alternateNames = {"bank_type", "bankType"})
    private String bankType;

    @Schema(description = "银行卡类型 00：借记 01：贷记 02：微信零钱 03：支付宝花呗 04：支付宝其他 05：数字货币 06：拉卡拉支付账户 99：未知")
    @JSONField(alternateNames = {"card_type", "cardType"})
    private String cardType;

    @Schema(description = "活动 ID")
    @JSONField(alternateNames = {"acc_activity_id", "accActivityId"})
    private String accActivityId;

    @Schema(description = "账户端返回信息域")
    @JSONField(alternateNames = {"acc_resp_fields", "accRespFields"})
    private Object accRespFields;

    @Schema
    @Data
    public static class SplitInfo{
        @Schema(description = "子单交易流水号")
        @JSONField(alternateNames = {"sub_trade_no", "subTradeNo"})
        private String subTradeNo;

        @Schema(description = "子单对账单流水号")
        @JSONField(alternateNames = {"sub_log_no", "subLogNo"})
        private String subLogNo;

        @Schema(description = "外部子交易流水号")
        @JSONField(alternateNames = {"out_sub_trade_no", "outSubTradeNo"})
        private String outSubTradeNo;

        @Schema(description = "商户号")
        @JSONField(alternateNames = {"merchant_no", "merchantNo"})
        private String merchantNo;

        @Schema(description = "商户名称")
        @JSONField(alternateNames = {"merchant_name", "merchantName"})
        private String merchantName;

        @Schema(description = "终端号")
        @JSONField(alternateNames = {"term_no", "termNo"})
        private String termNo;

        @Schema(description = "金额 单位分，整数型字符")
        private String amount;
    }

    @Data
    @Schema
    public static class AliPay{
        @Schema(description = "买家在支付宝的用户id")
        @JSONField(alternateNames = {"user_id", "userId"})
        private String userId;

        @Schema(description = "支付宝的店铺编号")
        @JSONField(alternateNames = {"alipay_store_id", "alipayStoreId"})
        private String alipayStoreId;

        @Schema(description = "交易支付使用的资金渠道")
        @JSONField(alternateNames = {"fund_bill_list", "fundBillList"})
        private String fundBillList;

        @Schema(description = "所有优惠券信息")
        @JSONField(alternateNames = {"voucher_detail_list", "voucherDetailList"})
        private String voucherDetailList;
    }

    @Data
    @Schema
    public static class WxPay{
        @Schema(description = "用户标识")
        @JSONField(alternateNames = {"open_id", "openId"})
        private String openId;

        @Schema(description = "用户子标识")
        @JSONField(alternateNames = {"user_id", "userId"})
        private String userId;

        @Schema(description = "活动 ID")
        @JSONField(alternateNames = {"acc_activity_id", "accActivityId"})
        private String accActivityId;

        @Schema(description = "优惠功能信息")
        @JSONField(alternateNames = {"promotion_detail", "promotionDetail"})
        private String promotionDetail;
    }

    @Data
    @Schema
    public static class UqrcodePay{

        @Schema(description = "用户子标识")
        @JSONField(alternateNames = {"user_id", "userId"})
        private String userId;

        @Schema(description = "银联单品营销 附加数据")
        @JSONField(alternateNames = {"up_iss_addn_data", "upIssAddnData"})
        private String upIssAddnData;

        @Schema(description = "银联优惠信息/出资方信息")
        @JSONField(alternateNames = {"up_coupon_info", "upCouponInfo"})
        private String upCouponInfo;
    }
}
