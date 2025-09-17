package com.wanmi.sbc.pay.request;

import com.wanmi.sbc.empower.bean.enums.LklPayType;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * @author edz
 * @className LakalaPayRequest
 * @description TODO
 * @date 2022/7/12 19:12
 **/
@Data
@Schema
public class LakalaPayItemRequest {
    @Schema(description = "订单ID，组合支付时此值为空")
    private String tid;

    @Schema(description = "组合订单ID。非组合支付时此值为空")
    private String parentTid;

    @Schema(description = "支付渠道")
    private PayGatewayEnum lakalaChannelItem;

    @Schema(description = "微信支付必填。其他渠道为空")
    private String openId;

    @Schema(description = "C端类型")
    private TerminalType terminal;

    @Schema(description = "渠道ID")
    private String channelItemId;

    @Schema(description = "支付宝、银联 支付成功跳转页面")
    private String successUrl;

    @Schema(description = "银联授权码")
    private String code;

    /**
     * 拉卡拉收银台支付方式
     */
    @Schema(description = "拉卡拉收银台支付方式")
    private LklPayType lklPayType;
}
