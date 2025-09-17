package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author edz
 * @className LakalaCasherNotifyRequest
 * @description TODO
 * @date 2023/7/25 15:47
 **/
@Data
@Schema
public class LakalaCasherNotifyRequest {

    @Schema(description = "支付订单号")
    @JSONField(name = "pay_order_no")
    private String payOrderNo;

    @Schema(description = "商户订单号")
    @JSONField(name = "out_order_no")
    private String outOrderNo;

    @Schema(description = "渠道号")
    @JSONField(name = "channel_id")
    private String channelId;

    @Schema(description = "交易商户号")
    @JSONField(name = "trans_merchant_no")
    private String transMerchantNo;

    @Schema(description = "交易终端号")
    @JSONField(name = "trans_term_no")
    private String transTermNo;

    @Schema(description = "结算商户号")
    @JSONField(name = "merchant_no")
    private String merchantNo;

    @Schema(description = "结算终端号")
    @JSONField(name = "term_no")
    private String termNo;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:支付成功 3:支付失败 4:已过期 5:已取消 6：部分退款或者全部退款")
    @JSONField(name = "order_status")
    private String orderStatus;

    @Schema(description = "订单金额 单位分，整数数字型字符")
    @JSONField(name = "total_amount")
    private String totalAmount;

    @Schema(description = "订单创建时间 格式yyyyMMddHHmmss")
    @JSONField(name = "order_create_time")
    private String orderCreateTime;

    @Schema(description = "订单有效时间 格式yyyyMMddHHmmss")
    @JSONField(name = "order_efficient_time")
    private String orderEfficientTime;

    @Schema(description = "合单标识 “1”为合单，不填默认是为非拆单")
    @JSONField(name = "split_mark")
    private String splitMark;

    @Schema(description = "收银台备注")
    @JSONField(name = "counter_remark")
    private String counterRemark;

    @Schema(description = "交易拆单信息")
    @JSONField(name = "split_info")
    private List<SplitInfoBean> splitInfo;

    @Schema(description = "订单交易信息")
    @JSONField(name = "order_trade_info")
    private OrderTradeInfoBean orderTradeInfo;

    /**
     * channel_id : 10
     * merchant_no : 05398007K
     * order_create_time : 20220117112533
     * order_efficient_time : 20220124112533
     * order_status : 2
     * order_trade_info : {"acc_trade_no":"2022011822001470651424354488","acc_type":"00","busi_type":"SCPAY","log_no":"66212380030451","pay_mode":"ALIPAY","payer_amount":1200,"settle_merchant_no":"82239105398007K","settle_term_no":"C9597363","trade_amount":1200,"trade_no":"20220118110113230166212380030451","trade_status":"S","trade_time":"20220118170046","trade_type":"PAY","user_id1":"135******50","user_id2":"2088312273770657"}
     * out_order_no : 3335ED2D29E04BB3B8D5AD696842B1BF
     * pay_order_no : 22011711012001101011025385338
     * split_info : [{"amount":"850","merchant_no":"05398007K","out_sub_order_no":"72FBA30FD4AB3A958C93CF72BDA21","sub_log_no":"66212379982975","sub_trade_no":"20220118110113230166212379982975","term_no":"C9597363"},{"amount":"350","merchant_no":"10541100CS","out_sub_order_no":"D7BF24DE245CDBC16B76B7FB3E7C1","sub_log_no":"66212380030458","sub_trade_no":"20220118110113230166212380030458","term_no":"D9486551"}]
     * split_mark : 1
     * term_no : C9597363
     * total_amount : 67200
     * trans_merchant_no : 05398007A
     * trans_term_no : D9587314
     */


    @Data
    public static class OrderTradeInfoBean {
        /**
         * acc_trade_no : 2022011822001470651424354488
         * acc_type : 00
         * busi_type : SCPAY
         * log_no : 66212380030451
         * pay_mode : ALIPAY
         * payer_amount : 1200
         * settle_merchant_no : 82239105398007K
         * settle_term_no : C9597363
         * trade_amount : 1200
         * trade_no : 20220118110113230166212380030451
         * trade_status : S
         * trade_time : 20220118170046
         * trade_type : PAY
         * user_id1 : 135******50
         * user_id2 : 2088312273770657
         */

        @Schema(description = "付款受理交易流水号")
        @JSONField(name = "acc_trade_no")
        private String accTradeNo;

        /**
         * busi_type为UPCARD时返回：00-借记卡,01-贷记卡,02-准贷记卡,03-预付卡
         * busi_type为SCPAY时返回：02-微信零钱,03-支付宝花呗,04-支付宝钱包,99-未知
         */
        @Schema(description = "账户类型")
        @JSONField(name = "acc_type")
        private String accType;

        /**
         * 支付业务类型：
         * UPCARD-银行卡
         * SCPAY-扫码支付
         * DCPAY-数币支付
         * ONLINE-线上支付
         */
        @Schema(description = "支付业务类型")
        @JSONField(name = "busi_type")
        private String busiType;

        @Schema(description = "对账单流水号")
        @JSONField(name = "log_no")
        private String logNo;

        /**
         * busi_type为SCPAY时返回：UQRCODEPAY-银联、WECHAT-微信、ALIPAY-支付宝
         */
        @Schema(description = "付款方式")
        @JSONField(name = "pay_mode")
        private String payMode;

        @Schema(description = "付款人实际支付金额，单位：分")
        @JSONField(name = "payer_amount")
        private int payerAmount;

        @Schema(description = "结算商户号")
        @JSONField(name = "settle_merchant_no")
        private String settleMerchantNo;

        @Schema(description = "结算终端号")
        @JSONField(name = "settle_term_no")
        private String settleTermNo;

        @Schema(description = "交易金额，单位：分")
        @JSONField(name = "trade_amount")
        private int tradeAmount;

        @Schema(description = "交易流水号")
        @JSONField(name = "trade_no")
        private String tradeNo;

        /**
         * 返回状态 S:成功 F:失败 C:被冲正 U:预记状态 X:发送失败 T: 发送超时 P: 处理中
         */
        @Schema(description = "支付状态")
        @JSONField(name = "trade_status")
        private String tradeStatus;

        @Schema(description = "交易完时间 格式yyyyMMddHHmmss")
        @JSONField(name = "trade_time")
        private String tradeTime;

        /**
         * PAY-消费 REFUND-退款 CANCEL-撤销
         */
        @Schema(description = "交易类型")
        @JSONField(name = "trade_type")
        private String tradeType;

        /**
         * 微信sub_open_id 支付宝buyer_logon_id（买家支付宝账号）
         */
        @Schema(description = "用户标识1")
        @JSONField(name = "user_id1")
        private String userId1;

        /**
         * 微信openId 支付宝buyer_user_id 银联user_id
         */
        @Schema(description = "用户标识2")
        @JSONField(name = "user_id2")
        private String userId2;

        @Schema(description = "付款人账号")
        @JSONField(name = "payer_account_no")
        private String payerAccountNo;

    }

    @Data
    public static class SplitInfoBean {
        /**
         * amount : 850
         * merchant_no : 05398007K
         * out_sub_order_no : 72FBA30FD4AB3A958C93CF72BDA21
         * sub_log_no : 66212379982975
         * sub_trade_no : 20220118110113230166212379982975
         * term_no : C9597363
         */
        @Schema(description = "金额 单位分，整数型字符")
        @JSONField(name = "amount")
        private String amount;

        @Schema(description = "拉卡拉分配的商户号")
        @JSONField(name = "merchant_no")
        private String merchantNo;

        @Schema(description = "商户子订单号，商户号下唯一")
        @JSONField(name = "out_sub_order_no")
        private String outSubOrderNo;

        @Schema(description = "子单对账单流水号")
        @JSONField(name = "subLogNo")
        private String subLogNo;

        @Schema(description = "子单交易流水号")
        @JSONField(name = "sub_trade_no")
        private String subTradeNo;

        @Schema(description = "拉卡拉分配的业务终端号")
        @JSONField(name = "term_no")
        private String termNo;
    }
}
