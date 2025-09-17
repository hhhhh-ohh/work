package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaIdCasherRefundRequest
 * @description TODO
 * @date 2022/7/5 22:07
 **/
@Data
@Schema
public class LakalaIdCasherRefundRequest {

    @JSONField(name = "merchant_no")
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    @Schema(description = "拉卡拉分配的业务终端号")
    @JSONField(name = "term_no")
    private String termNo;

    @Schema(description = "商户交易流水号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 单位分，整数数字型字符
     */
    @Schema(description = "退款金额")
    @JSONField(name = "refund_amount")
    private String refundAmount;

    /**
     * 原交易类型:1 银行卡，2 外卡，3 扫码（支付宝微信扫码），4 线上（网银）
     */
    @JSONField(name = "origin_biz_type")
    private String originBizType;

    /**
     * 原交易日期
     */
    @JSONField(name = "origin_trade_date")
    private String originTradeDate;

    @JSONField(name = "origin_card_no")
    private String originCardNo;

    @Schema(description = "退款原因")
    @JSONField(name = "refund_reason")
    private String refundReason;

    @Schema(description = "原拉卡拉交易流水号")
    @JSONField(name = "origin_trade_no")
    private String originTradeNo;

    @Schema(description = "原对账单流水号")
    @JSONField(name = "origin_log_no")
    private String originLogNo;

    @Schema(description = "地址位置信息，风控要求必送")
    @JSONField(name = "location_info")
    private LocationInfo locationInfo;

    @Data
    @Schema
    public static class LocationInfo {
        @Schema(description = "请求方的IP地址")
        @JSONField(name = "request_ip")
        private String requestIp;

        public LocationInfo(String requestIp) {
            this.requestIp = requestIp;
        }
    }
}
