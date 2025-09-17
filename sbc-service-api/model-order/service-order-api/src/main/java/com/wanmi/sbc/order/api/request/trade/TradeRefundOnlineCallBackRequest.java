package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.order.bean.enums.PayAndRefundCallBackServiceType;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TradePayOnlineCallBackRequest
 * @Description 订单退款回调
 * @Author Geek Wang
 * @Date 2020/7/2 14:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeRefundOnlineCallBackRequest extends BaseRequest {

    /**
     * 微信退款回调xml结果
     */
    @Schema(description = "微信退款回调xml结果")
    private String wxRefundCallBackResultXmlStr;

    /**
     * 微信退款回调结果
     */
    @Schema(description = "微信退款回调结果")
    private String wxRefundCallBackResultStr;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式0: 微信支付，1: 支付宝支付，2: 银联支付")
    private PayCallBackType payCallBackType;

    /**
     * 退款单号
     */
    @Schema(description = "退款单号")
    private String out_refund_no;

    /**
     * 银联退款回调结果
     */
    @Schema(description = "银联退款回调结果")
    private String unionRefundCallBackResultStr;

    @Schema(description = "视频好退款回调")
    private String wechatVideoRefundCallBackResultStr;

    @Schema(description = "拉卡拉退款结果")
    private String lakalaIdRefundRequest;

    @Schema(description = "拉卡拉收银台退款结果")
    private String lakalaCasherIdRefundRequest;

    /**
     * 微信退款回调结果
     */
    @Schema(description = "微信V3退款回调结果")
    private String wxV3RefundCallBackResultStr;

    public PayAndRefundCallBackServiceType getPayCallBackServiceType() {
        if(this.payCallBackType == PayCallBackType.WECAHT){
            return PayAndRefundCallBackServiceType.WECAHT;
        } else if(this.payCallBackType== PayCallBackType.ALI){
            return PayAndRefundCallBackServiceType.ALI;
        } else if(this.payCallBackType== PayCallBackType.UNIONPAY){
            return PayAndRefundCallBackServiceType.UNIONPAY;
        } else if(this.payCallBackType== PayCallBackType.WECHAT_CHANNELS){
            return PayAndRefundCallBackServiceType.WECHAT_CHANNELS;
        } else if (this.payCallBackType == PayCallBackType.LAKALA){
            return PayAndRefundCallBackServiceType.LAKALA;
        } else if (this.payCallBackType == PayCallBackType.WECAHT_V3){
            return PayAndRefundCallBackServiceType.WECAHT_V3;
        } else if (this.payCallBackType == PayCallBackType.LAKALA_CASHER){
            return PayAndRefundCallBackServiceType.LAKALA;
        }

        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
    }

}
