package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaIdRefundRequest
 * @description TODO
 * @date 2022/7/5 22:07
 **/
@Data
@Schema
public class LakalaIdRefundQueryRequest {
    @JSONField(name = "merchant_no")
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    @Schema(description = "拉卡拉分配的业务终端号")
    @JSONField(name = "term_no")
    private String termNo;

    @Schema(description = "商户退款订单号")
    @JSONField(name = "out_refund_order_no")
    private String outRefundOrderNo;

}
