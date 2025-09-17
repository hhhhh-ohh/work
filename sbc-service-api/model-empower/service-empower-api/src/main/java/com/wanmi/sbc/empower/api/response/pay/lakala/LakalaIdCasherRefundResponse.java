package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson.annotation.JSONField;

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
public class LakalaIdCasherRefundResponse {

//    @Schema(description = "商户号")
//    @JSONField(name = "merchant_no")
//    private String merchantNo;
    @Schema(description = "返回信息描述")
    @JSONField(name = "msg")
    private String msg;

    /**
     * 000000成功;
     * E710032(“710032”, “POSP退款请求失败”);
     * E710033(“710033”, “POSP退款失败”);
     * E710034(“710034”, “LABS退款请求失败”);
     * E710035(“710035”, “LABS退款失败”);
     * E710036(“710036”, “银行卡不能为空”);
     * E710037(“710037”, “该交易暂不支持线上退款”);
     * E710038(“710038”, “POSP退款中”)(posp返回68、96、C0);
     * E710039(“710039”, “LABS退款中”);
     * (Labs返回BBS11112、BBS11105）;
     * E710046(“710046”, “SAAS系统请求异常”);
     * E710047(“710047”, “SAAS退款失败”);
     * E710048(“710048”, “数币系统请求异常”);
     * E710049(“710049”, “数币退款失败”);
     * E710050(“710050”, “原交易拉卡拉交易订单号错误”);
     * E710051(“710051”, “原交易平台交易参考号错误”);
     * E710052(“710052”, “原交易拉卡拉交易订单号不能为空”);
     * E710053(“710053”, “系统参考号不能为空”)
     */
    @Schema(description = "返回码")
    @JSONField(name = "code")
    private String code;

    @Schema(description = "拉卡拉对账单流水号")
    @JSONField(name = "log_no")
    private String logNo;

    @Schema(description = "退款时间")
    @JSONField(name = "trade_time")
    private String tradeTime;

    @Schema(description = "请求中的商户请求流水号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

//    @Schema(description = "拉卡拉交易流水号")
//    @JSONField(name = "trade_no")
//    private String tradeNo;

//    @Schema(description = "钱包类型 微信：WECHAT 支付宝：ALIPAY 银联：UQRCODEPAY 翼支付: BESTPAY 苏宁易付宝: SUNING")
//    @JSONField(name = "account_type")
//    private String accountType;

    @Schema(description = "交易金额")
    @JSONField(name = "total_amount")
    private String totalAmount;

    @Schema(description = "申请退款金额")
    @JSONField(name = "refund_amount")
    private String refundAmount;

    @Schema(description = "实际退款金额")
    @JSONField(name = "payer_amount")
    private String payerAmount;

    @Schema(description = "账户端交易订单号")
    @JSONField(name = "acc_trade_no")
    private String accTradeNo;

    @Schema(description = "账户端交易订单号")
    @JSONField(name = "origin_log_no")
    private String originLogNo;

    @Schema(description = "账户端交易订单号")
    @JSONField(name = "origin_trade_no")
    private String originTradeNo;

    @Schema(description = "原商户请求流水号")
    @JSONField(name = "origin_out_trade_no")
    private String originOutTradeNo;

}
