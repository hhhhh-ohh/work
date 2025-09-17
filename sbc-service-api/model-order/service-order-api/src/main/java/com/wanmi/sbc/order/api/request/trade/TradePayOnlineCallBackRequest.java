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
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/7/2 14:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradePayOnlineCallBackRequest extends BaseRequest {

    /**
     * 微信支付回调结果
     */
    @Schema(description = "微信支付回调结果")
    private String wxPayCallBackResultStr;

    /**
     * 微信支付回调xml结果
     */
    @Schema(description = "微信支付回调xml结果")
    private String wxPayCallBackResultXmlStr;

    /**
     * 支付宝支付回调结果
     */
    @Schema(description = "支付宝支付回调结果")
    private String aliPayCallBackResultStr;

    /**
     * 微信视频号支付回调结果
     */
    @Schema(description = "微信视频号支付回调结果")
    private String wxChannelsPayCallBackBody;

    @Schema(description = "拉卡拉支付回调")
    private String lakalaPayCallBack;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式0: 微信支付，1: 支付宝支付，2: 银联支付，3：微信视频号")
    private PayCallBackType payCallBackType;

    /**
     * 银联支付回调结果
     */
    @Schema(description = "银联支付回调结果")
    private String unionPayCallBackResultStr;

    /**
     * 微信支付 V3
     */
    @Schema(description = "微信-V3支付回调结果")
    private String wxPayV3CallBackBody;

    public PayAndRefundCallBackServiceType getPayCallBackServiceType() {
        if(this.payCallBackType == PayCallBackType.WECAHT){
            return PayAndRefundCallBackServiceType.WECAHT;
        } else if(this.payCallBackType== PayCallBackType.ALI){
            return PayAndRefundCallBackServiceType.ALI;
        } else if(this.payCallBackType== PayCallBackType.UNIONPAY){
            return PayAndRefundCallBackServiceType.UNIONPAY;
        }else if(this.payCallBackType== PayCallBackType.WECHAT_CHANNELS){
            return PayAndRefundCallBackServiceType.WECHAT_CHANNELS;
        } else if (this.payCallBackType == PayCallBackType.LAKALA){
            return PayAndRefundCallBackServiceType.LAKALA;
        } else if (this.payCallBackType == PayCallBackType.WECAHT_V3){
            return PayAndRefundCallBackServiceType.WECAHT_V3;
        } else if (this.payCallBackType == PayCallBackType.LAKALA_CASHER){
            return PayAndRefundCallBackServiceType.LAKALA_CASHER;
        }
        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
    }
}
