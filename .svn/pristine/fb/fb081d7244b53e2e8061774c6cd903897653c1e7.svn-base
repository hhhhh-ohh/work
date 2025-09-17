package com.wanmi.sbc.order.api.request.trade;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 更新小程序订阅消息发送状态
 * @author xufeng
 * @date 2022/8/23 16:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class OrderModifySendFlagRequest implements Serializable {

    private static final long serialVersionUID = 1130113166367554278L;
    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    private String tid;

    /**
     * 尾款开始支付
     */
    @Schema(description = "尾款开始支付")
    private Boolean bookingStartSendFlag = Boolean.FALSE;

    /**
     * 尾款支付超时提醒
     */
    @Schema(description = "尾款支付超时提醒")
    private Boolean bookingEndSendFlag = Boolean.FALSE;
}
