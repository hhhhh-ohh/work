package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaTradeQueryRequest
 * @description TODO
 * @date 2022/7/5 21:20
 */
@Schema
@Data
public class LakalaTradeQueryRequest {
    @JSONField(name = "merchant_no")
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    @Schema(description = "拉卡拉分配的业务终端号")
    @JSONField(name = "term_no")
    private String termNo;

    @Schema(description = "商户交易流水号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @Schema(description = "原交易拉卡拉交易流水号")
    @JSONField(name = "trade_no")
    private String tradeNo;
}
