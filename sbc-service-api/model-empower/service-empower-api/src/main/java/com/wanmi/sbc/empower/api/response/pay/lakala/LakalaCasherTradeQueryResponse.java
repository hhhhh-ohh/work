package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author zhangxiaodong
 */
@Data
@Schema
public class LakalaCasherTradeQueryResponse implements Serializable {


    private static final long serialVersionUID = -1596988311618415198L;

    /**
     * pay_order_no : 21092211012001970631000488042
     * out_order_no : LABS1632300253YDMG
     * channel_id : 15
     * trans_merchant_no : 82216205947000G
     * trans_term_no : D0060389
     * merchant_no : 82216205947000G
     * term_no : D0060389
     * order_status : 2
     * order_info : 24865454154
     * total_amount : 3300
     * order_create_time : 20210922164413
     * order_efficient_time : 20221208165845
     * order_trade_info_list : [{"trade_no":"2021092251210203410010","log_No":"51210203410010","trade_type":"PAY","trade_status":"S","trade_amount":3300,"payer_amount":0,"user_id1":"","user_id2":"","busi_type":"ONLINE","trade_time":"2021092264452","acc_trade_no":"109221009853","payer_account_no":"","payer_name":"","payer_account_bank":"","acc_type":"99","pay_mode":"LKLAT"}]
     */

    @Schema(description = "支付订单号")
    @JSONField(alternateNames = {"pay_order_no", "payOrderNo"})
    private String payOrderNo;

    @Schema(description = "商户订单号")
    @JSONField(alternateNames = {"out_order_no", "outOrderNo"})
    private String outOrderNo;

    @Schema(description = "渠道号")
    @JSONField(alternateNames = {"channel_id", "channelId"})
    private String channelId;

    @Schema(description = "交易商户号")
    @JSONField(alternateNames = {"trans_merchant_no", "transMerchantNo"})
    private String transMerchantNo;

    @Schema(description = "交易终端号")
    @JSONField(alternateNames = {"trans_term_no", "transTermNo"})
    private String transTermNo;

    @Schema(description = "结算商户号（合单订单中该结算商户号为主单名义上结算商户号）")
    @JSONField(alternateNames = {"merchant_no", "merchantNo"})
    private String merchantNo;

    @Schema(description = "结算终端号（合单订单中该结算商户号为主单名义上结算终端号）")
    @JSONField(alternateNames = {"term_no", "termNo"})
    private String termNo;

    /**
     * 0:待支付 1:支付中 2:支付成功 3:支付失败 4:已过期 5:已取消 6：部分退款或者全部退款
     */
    @Schema(description = "订单状态")
    @JSONField(alternateNames = {"order_status", "orderStatus"})
    private String orderStatus;

    @Schema(description = "订单描述")
    @JSONField(alternateNames = {"order_info", "orderInfo"})
    private String orderInfo;

    @Schema(description = "订单金额，单位：分")
    @JSONField(alternateNames = {"total_amount", "totalAmount"})
    private int totalAmount;

    @Schema(description = "订单创建时间")
    @JSONField(alternateNames = {"order_create_time", "orderCreateTime"})
    private String orderCreateTime;

    @Schema(description = "订单有效时间")
    @JSONField(alternateNames = {"order_efficient_time", "orderEfficientTime"})
    private String orderEfficientTime;

    @Schema(description = "结算类型（非合单） （“0”或者空，常规结算方式）")
    @JSONField(alternateNames = {"settle_type", "settleType"})
    private String settleType;

    @Schema(description = "合单标识")
    @JSONField(alternateNames = {"split_mark", "splitMark"})
    private String splitMark;

    @Schema(description = "支付订单号")
    @JSONField(alternateNames = {"order_trade_info_list", "orderTradeInfoList"})
    private List<OrderTradeInfoListBean> orderTradeInfoList;

    @Schema(description = "收银台备注")
    @JSONField(name = "counter_remark")
    private String counterRemark;

    @Schema(description = "商户拆单信息")
    @JSONField(alternateNames = {"out_split_info", "outSplitInfo"})
    private List<OutSplitInfoBean> outSplitInfo;


    @Schema(description = "交易拆单信息")
    @JSONField(alternateNames = {"split_info", "splitInfo"})
    private List<SplitInfoBean> splitInfo;

    @Schema(description = "json字符串 收银台参数")
    @JSONField(name = "counter_remark")
    private String counterParam;


    @Schema(description = "json字符串 业务类型控制参数")
    @JSONField(name = "busi_type_param")
    private String busiTypeParam;


    @Data
    public static class OrderTradeInfoListBean {
        /**
         * trade_no : 2021092251210203410010
         * log_No : 51210203410010
         * trade_type : PAY
         * trade_status : S
         * trade_amount : 3300
         * payer_amount : 0
         * user_id1 :
         * user_id2 :
         * busi_type : ONLINE
         * trade_time : 2021092264452
         * acc_trade_no : 109221009853
         * payer_account_no :
         * payer_name :
         * payer_account_bank :
         * acc_type : 99
         * pay_mode : LKLAT
         */
        @Schema(description = "交易流水号")
        @JSONField(alternateNames = {"trade_no", "tradeNo"})
        private String tradeNo;

        @Schema(description = "对账单流水号")
        @JSONField(alternateNames = {"log_No", "logNo"})
        private String logNo;

        @Schema(description = "交易类型")
        @JSONField(alternateNames = {"trade_type", "tradeType"})
        private String tradeType;

        @Schema(description = "支付状态")
        @JSONField(alternateNames = {"trade_status", "tradeStatus"})
        private String tradeStatus;

        @Schema(description = "交易金额，单位：分")
        @JSONField(alternateNames = {"trade_amount", "tradeAmount"})
        private int tradeAmount;

        @Schema(description = "付款人实际支付金额，单位：分")
        @JSONField(alternateNames = {"payer_amount", "payerAmount"})
        private int payerAmount;

        @Schema(description = "用户标识1 微信sub_open_id 支付宝buyer_logon_id（买家支付宝账号）")
        @JSONField(alternateNames = {"user_id1", "userId1"})
        private String userId1;

        @Schema(description = "用户标识2 微信openId 支付宝buyer_user_id 银联user_id")
        @JSONField(alternateNames = {"user_id2", "userId2"})
        private String userId2;

        /**
         * 支付业务类型：
         * UPCARD-银行卡
         * SCPAY-扫码支付
         * DCPAY-数币支付
         * ONLINE-线上支付
         */
        @Schema(description = "支付业务类型")
        @JSONField(alternateNames = {"busi_type", "busiType"})
        private String busiType;

        @Schema(description = "交易完时间")
        @JSONField(alternateNames = {"trade_time", "tradeTime"})
        private String tradeTime;

        @Schema(description = "付款受理交易流水号")
        @JSONField(alternateNames = {"acc_trade_no", "accTradeNo"})
        private String accTradeNo;

        @Schema(description = "付款人账号")
        @JSONField(alternateNames = {"payer_account_no", "payerAccountNo"})
        private String payerAccountNo;

        @Schema(description = "付款人名称（仅ONLINE交易返回）")
        @JSONField(alternateNames = {"payer_name", "payerName"})
        private String payerName;

        @Schema(description = "付款账号开户行")
        @JSONField(alternateNames = {"payer_account_bank", "payerAccountBank"})
        private String payerAccountBank;

        @Schema(description = "账户类型")
        @JSONField(alternateNames = {"acc_type", "accType"})
        private String accType;

        @Schema(description = "付款方式")
        @JSONField(alternateNames = {"pay_mode", "payMode"})
        private String payMode;
    }


    @Schema
    @Data
    public static class OutSplitInfoBean {
        @Schema(description = "外部子订单号")
        @JSONField(alternateNames = {"out_sub_order_no", "outSubOrderNo"})
        private String outSubOrderNo;

        @Schema(description = "商户号")
        @JSONField(alternateNames = {"merchant_no", "merchantNo"})
        private String merchantNo;

        @Schema(description = "终端号")
        @JSONField(alternateNames = {"term_no", "termNo"})
        private String termNo;

        @Schema(description = "金额 单位分，整数型字符")
        private String amount;
    }

    @Schema
    @Data
    public static class SplitInfoBean {
        @Schema(description = "子单交易流水号")
        @JSONField(alternateNames = {"sub_trade_no", "subTradeNo"})
        private String subTradeNo;

        @Schema(description = "子单对账单流水号")
        @JSONField(alternateNames = {"sub_log_no", "subLogNo"})
        private String subLogNo;

        @Schema(description = "外部子订单号")
        @JSONField(alternateNames = {"out_sub_order_no", "outSubOrderNo"})
        private String outSubOrderNo;

        @Schema(description = "商户号")
        @JSONField(alternateNames = {"merchant_no", "merchantNo"})
        private String merchantNo;

        @Schema(description = "终端号")
        @JSONField(alternateNames = {"term_no", "termNo"})
        private String termNo;

        @Schema(description = "金额 单位分，整数型字符")
        private String amount;
    }
}
