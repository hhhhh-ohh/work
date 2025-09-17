package com.wanmi.sbc.pay.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author zhanggaolei
 * @type PayChannelRequest.java
 * @desc
 * @date 2022/11/17 11:35
 */
@Data
public class PayChannelRequest extends BaseRequest {

    /**
     * 订单id，单笔支付必传
     */
    @NotNull
    @Schema(description = "订单号，支付必传，可以是父单号，订单号，付费会员单号，授信支付单号",required = true)
    private String id;

    /**
     * 微信JSApi支付时必传
     */
    @Schema(description = "微信JSApi支付时必传")
    private String openid;

    @NotNull
    @Schema(description = "支付类型，",required = true)
    private PayChannelType payChannelType;

    @Schema(description = "渠道ID")
    private Long channelItemId;

    @Enumerated
    @Schema(description = "终端类型", example="0:pc 1:h5 2:app")
    private TerminalType terminal = TerminalType.H5;
    /**
     * 支付成功后的前端回调url
     */
    @Schema(description = "支付成功后的前端回调url",  example= "String")
    private String successUrl;

    /**
     * 拉卡拉银联支付必填
     */
    @Schema(description = "银联授权码")
    private String code;


    /**
     * 当使用 授信/余额 支付的时候必填
     */
    @Schema(description = "支付密码，当使用 授信/余额支付 的时候必填")
    private String payPassword;

}
