package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zxd
 * @className LakalaCasherAllPayRequest
 * @description TODO
 * @date 2023/7/24 10:46
 */
@Data
@Schema
public class LakalaCasherAllPayRequest implements Serializable {

    private static final long serialVersionUID = 1430761993591320653L;

    /**
     * out_order_no : KFPT20220714160009228907288
     * merchant_no : 8222900701106PZ
     * vpos_id : 587305941625155584
     * channel_id : 2021052614391
     * total_amount : 1
     * busi_mode : PAY
     * order_efficient_time : 20220714170009
     * notify_url : http://run.mocky.io/v3/b02c9448-20a2-4ff6-a678-38ecab30161d
     * support_cancel : 0
     * support_refund : 1
     * support_repeat_pay : 1
     * busi_type_param : [{"busi_type":"UPCARD","params":{"crd_flg":"CRDFLG_D|CRDFLG_C|CRDFLG_OTH"}},{"busi_type":"SCPAY","params":{"pay_mode":"WECHAT","crd_flg":"CRDFLG_D"}}]
     * counter_param : {"pay_mode":"ALIPAY"}
     * out_user_id :
     * order_info : 自动化测试
     * extend_info : 自动化测试
     * callback_url :
     */

    @JSONField(name = "out_order_no")
    @Schema(description = "商户订单号")
    private String outOrderNo;

    @JSONField(name = "merchant_no")
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    @Schema(description = "拉卡拉分配的业务终端号,结算终端号,合单场景必输该字段")
    @JSONField(name = "term_no")
    private String termNo;

    @JSONField(name = "vpos_id")
    @Schema(description = "交易设备标识（非合单场景必输该字段），进件返回接口中的termId字段，非API接口进件请联系业务员。")
    private String vposId;

    @JSONField(name = "channel_id")
    @Schema(description = "渠道号 （一般不用）")
    private String channelId;

    @JSONField(name = "total_amount")
    @Schema(description = "订单金额，单位：分”")
    private String totalAmount;

    @JSONField(name = "busi_mode")
    @Schema(description = "业务模式： ACQ-收单 默认为“ACQ-收单”")
    private String busiMode;

    @JSONField(name = "order_efficient_time")
    @Schema(description = "订单有效期 格式yyyyMMddHHmmss,最大支持下单时间+2天")
    private String orderEfficientTime;

    @JSONField(name = "notify_url")
    @Schema(description = "订单支付成功后商户接收订单通知的地址 http://xxx.xxx.com")
    private String notifyUrl;

    @JSONField(name = "support_cancel")
    @Schema(description = "是否支持撤销 默认 0 不支持 busi_mode为“PAY-付款”不支持 撤销")
    private String supportCancel;

    @JSONField(name = "support_refund")
    @Schema(description = "是否支持退款 默认0 不支持")
    private String supportRefund;

    @JSONField(name = "support_repeat_pay")
    @Schema(description = "是否支持“多次发起支付” 默认0 不支持")
    private String supportRepeatPay;

    /**
     * [{\“busi_type\“:\“UPCARD\“,\“params\“:{\“crd_flg\“:\“CRDFLG_D|CRDFLG_C|CRDFLG_OTH\“}},{\“busi_type\“:\“SCPAY\“,\“params\“:{\“pay_mode\“:\“ALIPAY\“,\“crd_flg\“:\“CRDFLG_D|CRDFLG_C\“}},{\“busi_type\“:\“DCPAY\“,\“params\“:{\“pay_mode\“:\“DCPAY\“}}]
     * 说明：UPCARD-刷卡，SCPAY-扫码，DCPAY-数币 CRDFLG_D-借记卡，CRDFLG_C-贷记卡，CRDFLG_OTH-不明确是借记卡还是贷记卡(仅UPCARD使用）
     * pay_mode送参说明：ALIPAY-支付宝，WECHAT-微信，UNION-银联二维码，DCPAY-数字货币，BESTPAY-翼支付
     * 说明：一旦使用该字段，则增加限制，必须在指定限制范围内支付。比如，只配置”busi_type”:”UPCARD”的参数而不配置”busi_type”:”SCPAY”的参数，则只能通过刷卡而不能通过扫码完成支付
     */
    @JSONField(name = "busi_type_param")
    @Schema(description = "业务类型控制参数，jsonStr格式")
    private String busiTypeParam;

    /**
     * {\“pay_mode\“ : \“ALIPAY\“} ，指定支付方式为支付宝
     * ALIPAY：支付宝
     * WECHAT：微信
     * UNION：银联云闪付
     * CARD：POS刷卡交易
     * LKLAT：线上转帐
     * QUICK_PAY：快捷支付
     * EBANK：网银支付
     * UNION_CC：银联支付
     */
    @JSONField(name = "counter_param")
    @Schema(description = "json字符串 收银台展示参数")
    private String counterParam;

    @JSONField(name = "out_user_id")
    @Schema(description = "发起订单方的userId，归属于channelId下的userId")
    private String outUserId;

    @JSONField(name = "order_info")
    @Schema(description = "订单标题，在使用收银台扫码支付时必输入，交易时送往账户端")
    private String orderInfo;

    @Schema(description = "客户端下单完成支付后返回的商户网页跳转地址。")
    @JSONField(name = "callback_url")
    private String callbackUrl;

    @Schema(description = "结算类型:“0”或者空，常规结算方式，如需接拉卡拉分账需传“1”；")
    @JSONField(name = "settle_type")
    private String settleType = "1";

    @Schema(description = "合单标识，“1”为合单，不填默认是为非合单")
    @JSONField(name = "split_mark")
    private String splitMark;

    @Schema(description = "收银台备注")
    @JSONField(name = "counter_remark")
    private String counterRemark;

    @Schema(description = "拆单信息,合单标识为“1”时必传该字段。,详细字段见out_split_info字段说明")
    @JSONField(name = "out_split_info")
    private List<OutSplitInfoBean> outSplitInfo;


    @Schema
    @Data
    public static class OutSplitInfoBean {

        @Schema(description = "外部子交易流水号")
        @JSONField(name = "out_sub_order_no")
        private String outSubOrderNo;

        @Schema(description = "商户号")
        @JSONField(name = "merchant_no")
        private String merchantNo;

        @Schema(description = "终端号")
        @JSONField(name = "term_no")
        private String termNo;

        @Schema(description = "金额 单位分")
        @JSONField(name = "amount")
        private String amount;

    }
}
