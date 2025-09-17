package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaCloseResponse
 * @description TODO
 * @date 2022/7/5 21:12
 **/
@Data
@Schema
public class LakalaCloseResponse {
    @Schema(description = "原拉卡拉订单号")
    @JSONField(name = "origin_trade_no")
    private String originTradeNo;

    @Schema(description = "原商户请求流水号")
    @JSONField(name = "origin_out_trade_no")
    private String originOutTradeNo;

    @Schema(description = "原订单外部订单来源")
    @JSONField(name = "origin_out_order_source")
    private String originOutOrderSource;

    @Schema(description = "原订单外部商户订单号")
    @JSONField(name = "origin_out_order_no")
    private String originOutOrderNo;

    @Schema(description = "交易时间")
    @JSONField(name = "trade_time")
    private String tradeTime;
}
