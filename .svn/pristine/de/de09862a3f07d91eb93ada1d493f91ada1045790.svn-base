package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * @author edz
 * @className LakalaPayRequest
 * @description TODO
 * @date 2022/6/30 10:46
 */
@Data
@Schema
public class LakalaCloseRequest implements Serializable {

    @JSONField(name = "merchant_no")
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    @Schema(description = "拉卡拉分配的业务终端号")
    @JSONField(name = "term_no")
    private String termNo;

    @Schema(description = "原商户交易流水号")
    @JSONField(name = "origin_out_trade_no")
    private String originOutTradeNo;

    @Schema(description = "原交易拉卡拉交易流水号")
    @JSONField(name = "origin_trade_no")
    private String originTradeNo;


    @Schema(description = "地址位置信息，风控要求必送")
    @JSONField(name = "location_info")
    private LocationInfo locationInfo;

    @Data
    @Schema
    public static class LocationInfo {
        @Schema(description = "请求方的IP地址")
        @JSONField(name = "request_ip")
        private String requestIp;
    }
}
