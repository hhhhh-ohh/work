package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaCasherAllPayResponse
 * @description TODO
 * @date 2023/7/24 14:29
 **/
@Data
@Schema
public class LakalaCasherAllPayResponse {

    @Schema(description = "商户号")
    @JSONField(alternateNames = {"merchant_no", "merchantNo"})
    private String merchantNo;

    @Schema(description = "channelId")
    @JSONField(alternateNames = {"channel_id", "channelId"})
    private String channelId;

    @Schema(description = "商户订单号")
    @JSONField(alternateNames = {"out_order_no", "outOrderNo"})
    private String outOrderNo;

    /**
     * 订单系统创建订单的时间，格式yyyyMMddHHmmss
     */
    @Schema(description = "创建订单时间")
    @JSONField(alternateNames = {"order_create_time", "orderCreateTime"})
    private String orderCreateTime;

    @Schema(description = "订单有效截至时间")
    @JSONField(alternateNames = {"order_efficient_time", "orderEfficientTime"})
    private String orderEfficientTime;

    @Schema(description = "平台订单号")
    @JSONField(alternateNames = {"pay_order_no", "payOrderNo"})
    private String payOrderNo;

    @Schema(description = "订单金额，单位：分")
    @JSONField(alternateNames = {"total_amount", "totalAmount"})
    private String totalAmount;

    @Schema(description = "收银台地址信息")
    @JSONField(alternateNames = {"counter_url", "counterUrl"})
    private String counterUrl;

}
