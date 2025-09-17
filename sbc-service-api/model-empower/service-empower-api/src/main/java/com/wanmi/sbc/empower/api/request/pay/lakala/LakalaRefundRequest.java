package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaRefundRequest
 * @description TODO
 * @date 2022/7/5 20:58
 */
@Data
@Schema
public class LakalaRefundRequest {
    @JSONField(name = "merchant_no")
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    @Schema(description = "拉卡拉分配的业务终端号")
    @JSONField(name = "term_no")
    private String termNo;

    @Schema(description = "商户系统唯一订单号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @Schema(description = "单位分，整数数字型字符")
    @JSONField(name = "refund_amount")
    private String refundAmount;

    @Schema(description = "退款原因")
    @JSONField(name = "refund_reason")
    private String refundReason;

    @Schema(description = "原商户交易流水号")
    @JSONField(name = "origin_out_trade_no")
    private String originOutTradeNo;

    @Schema(description = "原拉卡拉交易流水号")
    @JSONField(name = "origin_trade_no")
    private String originTradeNo;

    @Schema(description = "原对账单流水号")
    @JSONField(name = "origin_log_no")
    private String originLogNo;

    @Schema(description = "地址位置信息")
    @JSONField(name = "location_info")
    private Object locationInfo;

    @Data
    @Schema
    public static class LocationInfo {
        @Schema(description = "请求方的IP地址")
        @JSONField(name = "request_ip")
        private String requestIp;
    }
}
