package com.wanmi.sbc.empower.pay.service.wechat.v3.request;

import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayRefundRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @description  关闭支付单
 * @author  wur
 * @date: 2022/11/28 13:50
 **/
@NoArgsConstructor
@Data
public class WxPayV3RefundsRequest implements Serializable {

    private static final long serialVersionUID = 7736763604536062258L;

    /**
     * 微信支付订单号 和 transaction_id 二选一
     */
    private String transaction_id;

    /**
     *  商户订单号 和 transaction_id 二选一
     */
    private String out_trade_no;

    /**
     *  退款单号
     */
    private String out_refund_no;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 退款回调通知
     */
    private String notify_url;

    /**
     * 退款金额信息
     */
    private WxRefundAmount amount;

    public WxPayV3RefundsRequest(WxPayRefundRequest refundRequest) {
        this.setOut_trade_no(refundRequest.getOut_trade_no());
        this.setOut_refund_no(refundRequest.getOut_refund_no());
        this.setReason(refundRequest.getRefund_desc());
        this.amount = new WxRefundAmount();
        amount.setRefund(Integer.valueOf(refundRequest.getRefund_fee()));
        amount.setTotal(Integer.valueOf(refundRequest.getTotal_fee()));
    }

}
