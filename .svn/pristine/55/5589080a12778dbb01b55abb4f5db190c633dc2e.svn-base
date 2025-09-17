package com.wanmi.sbc.empower.api.request.pay.unioncloud;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Auther: jiaojiao
 * @Date: 2018/10/16 10:18
 * @Description:
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class
UnionPayRequest extends PayBaseRequest {

    private static final long serialVersionUID = 5476012447108714917L;
    /**
     * 业务订(退)单号
     */
    @Schema(description = "业务订(退)单号")
    @NotBlank
    private String outTradeNo;
    /**
     * 终端
     */
    @Schema(description = "终端")
    @NotNull
    private TerminalType terminal;

    /**
     * 支付项(具体支付渠道项)id
     */
    @Schema(description = "支付项(具体支付渠道项)id")
    @NotNull
    private Long channelItemId;

    /**
     * 订单交易总金额，单位元
     */
    @Schema(description = "订单交易总金额，单位元")
    @NotNull
    @Min(1L)
    private BigDecimal amount;

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
     * 前台页面跳转地址
     */
    @Schema(description = "前台页面跳转地址")
    private String frontUrl;

    /**
     * 异步通知地址
     */
    @Schema(description = "异步通知地址")
    private String notifyUrl;



    /**
     * 商户号
     */
    @Schema(description = "商户号")
    private String apiKey;

    /**
     * 编码
     */
    @Schema(description = "编码")
    private String encoding;
    /**
     * 订单支付发送时间戳
     */
    @Schema(description = "订单支付发送时间戳")
    private String txnTime;

    /**
     * 发起支付请求的客户端ip
     */
    @Schema(description = "发起支付请求的客户端ip")
    @NotBlank
    private String clientIp;


    /**
     * 超时未支付取消订单时间
     */
    @Schema(description = "超时未支付取消订单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTimeOut;

    /**
     * 是否授信还款支付
     */
    @Schema(description = "是否授信还款支付")
    private Boolean creditRepayFlag = Boolean.FALSE;
}
