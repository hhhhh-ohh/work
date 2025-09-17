package com.wanmi.sbc.empower.api.request.pay;

import com.wanmi.sbc.empower.api.request.pay.ali.AliPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaIdCasherRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaIdRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unionb2b.UnionB2bPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionCloudPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxChannelsPayRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayRefundRequest;
import com.wanmi.sbc.empower.bean.enums.PayType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayRefundBaseRequest implements Serializable {

    /**
     * 银联B2B退款接口参数
     */
    @Schema(description = "银联B2B退款接口参数")
    private UnionB2bPayRefundRequest unionB2bPayRefundRequest;

    /**
     * 支付宝退款接口参数
     */
    @Schema(description = " 支付宝退款接口参数")
    private AliPayRefundRequest aliPayRefundRequest;

    /**
     * 银联云闪付退款接口参数
     */
    @Schema(description = "银联云闪付退款接口参数")
    private UnionCloudPayRefundRequest unionCloudPayRefundRequest;

    /**
     * 微信支付退款接口参数
     */
    @Schema(description = "微信支付退款接口参数")
    private WxPayRefundRequest wxPayRefundRequest;

    /**
     * 微信支付退款接口参数
     */
    @Schema(description = "微信支付退款接口参数")
    private WxChannelsPayRefundRequest wxChannelsRefundRequest;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayType payType;

    @Schema(description = "拉卡拉退款交易接口参数")
    private LakalaIdRefundRequest lakalaIdRefundRequest;

    @Schema(description = "拉卡拉收银台退款交易接口参数")
    private LakalaIdCasherRefundRequest lakalaIdCasherRefundRequest;
}
