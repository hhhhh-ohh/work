package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaCasherCloseRequest
 * @description TODO
 * @date 2023/07/24 16:59
 */
@Data
@Schema
public class LakalaCasherCloseRequest implements Serializable {

    private static final long serialVersionUID = 3031634168209202957L;

    @JSONField(name = "merchant_no")
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    @Schema(description = "商户订单号")
    @JSONField(name = "out_order_no")
    private String outOrderNo;

    @Schema(description = "支付订单号")
    @JSONField(name = "pay_order_no")
    private String payOrderNo;

    @Schema(description = "渠道号")
    @JSONField(name = "channel_id")
    private String channelId;

}
