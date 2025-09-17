package com.wanmi.sbc.empower.api.request.pay;

import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>支付请求参数</p>
 * Created by of628-wenzhi on 2017-08-04-下午5:59.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class PayRequest extends PayBaseRequest {

    private static final long serialVersionUID = -72656336122565880L;
    /**
     * 业务订(退)单号
     */
    @Schema(description = "业务订(退)单号")
    @NotBlank
    private String outTradeNo;

    /**
     * 支付项(具体支付渠道项)id
     */
    @Schema(description = "支付项(具体支付渠道项)id")
    @NotNull
    private Long channelItemId;

    /**
     * 终端
     */
    @Schema(description = "终端")
    @NotNull
    private TerminalType terminal;

    /**
     * 订单交易总金额，单位元
     */
    @Schema(description = "订单交易总金额，单位元")
    @NotNull
//    @Min(1L)
    private BigDecimal amount;

    /**
     * 发起支付请求的客户端ip
     */
    @Schema(description = "发起支付请求的客户端ip")
    @NotBlank
    private String clientIp;

    /**
     * 商品标题
     */
    @Schema(description = "商品标题")
    @NotBlank
    private String subject;

    /**
     * 商品描述信息
     */
    @Schema(description = "商品描述信息")
    @NotBlank
    private String body;

    /**
     * 微信支付时必传，付款用户在商户 appid 下的唯一标识
     */
    @Schema(description = "微信支付时必传，付款用户在商户 appid 下的唯一标识")
    private String openId;


    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "商户id-boss端取默认值")
    @NotNull
    private Long storeId;

    /**
     * 是否授信还款支付
     */
    @Schema(description = "是否授信还款支付")
    private Boolean creditRepayFlag = Boolean.FALSE;
}
