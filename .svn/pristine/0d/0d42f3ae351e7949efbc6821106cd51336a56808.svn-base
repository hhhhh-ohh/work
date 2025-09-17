package com.wanmi.sbc.order.bean.vo;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author
 * @className LklOrderTradeInfoVO
 * @description
 * @date 2023/07/26 10:00
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LklOrderTradeInfoVO implements Serializable {

    private static final long serialVersionUID = 1328241158263522364L;

    /**
     * 拉卡拉分配的商户号
     */
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    /**
     * 终端号
     */
    @Schema(description = "终端号")
    private String termNo;

    /**
     * 对账单流水号
     */
    @Schema(description = "对账单流水号")
    private String logNo;

    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;


    /**
     * 预售尾款对账单流水号
     */
    @Schema(description = "预售尾款对账单流水号")
    private String tailLogNo;

    /**
     * 预售尾款交易流水号
     */
    @Schema(description = "预售尾款交易流水号")
    private String tailTradeNo;

    @Schema(description = "付款受理交易流水号")
    private String accTradeNo;

    /**
     * busi_type为UPCARD时返回：00-借记卡,01-贷记卡,02-准贷记卡,03-预付卡
     * busi_type为SCPAY时返回：02-微信零钱,03-支付宝花呗,04-支付宝钱包,99-未知
     */
    @Schema(description = "账户类型")
    private String accType;

    /**
     * 支付业务类型：
     * UPCARD-银行卡
     * SCPAY-扫码支付
     * DCPAY-数币支付
     * ONLINE-线上支付
     */
    @Schema(description = "支付业务类型")
    private String busiType;

    /**
     * busi_type为SCPAY时返回：UQRCODEPAY-银联、WECHAT-微信、ALIPAY-支付宝
     */
    @Schema(description = "付款方式")
    private String payMode;

    @Schema(description = "付款人实际支付金额，单位：分")
    private int payerAmount;

    @Schema(description = "结算商户号")
    private String settleMerchantNo;

    @Schema(description = "结算终端号")
    private String settleTermNo;

    @Schema(description = "交易金额，单位：分")
    private int tradeAmount;

    /**
     * 返回状态 S:成功 F:失败 C:被冲正 U:预记状态 X:发送失败 T: 发送超时 P: 处理中
     */
    @Schema(description = "支付状态")
    private String tradeStatus;

    @Schema(description = "交易完时间 格式yyyyMMddHHmmss")
    private String tradeTime;

    /**
     * PAY-消费 REFUND-退款 CANCEL-撤销
     */
    @Schema(description = "交易类型")
    private String tradeType;

    /**
     * 微信sub_open_id 支付宝buyer_logon_id（买家支付宝账号）
     */
    @Schema(description = "用户标识1")
    private String userId1;

    /**
     * 微信openId 支付宝buyer_user_id 银联user_id
     */
    @Schema(description = "用户标识2")
    private String userId2;

    /**
     * 付款人账号
     */
    private String payerAccountNo;
}
