package com.wanmi.sbc.pay.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaWeChatPayRequest
 * @description TODO
 * @date 2022/7/22 10:56
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LakalaWeChatPayRequest implements Serializable {
    @Schema(description = "表明来源")
    @Builder.Default
    private String source = "PC";

    @Schema(description = "临时登录code")
    private String temporaryCode;

    @Schema(description = "渠道ID", required = true)
    private String channelItemId;

    @Schema(description = "支付渠道 0银联云闪付、1微信支付、2支付宝支付")
    @Builder.Default
    private String lakalaChannelItem = "1";

    @Schema(description = "组合订单ID，非组合支付时此值为空")
    private String parentTid;

    @Schema(description = "订单ID，组合支付时此值为空")
    private String tid;

    @Schema(description = "订单金额")
    private String paymentPrice;
}
