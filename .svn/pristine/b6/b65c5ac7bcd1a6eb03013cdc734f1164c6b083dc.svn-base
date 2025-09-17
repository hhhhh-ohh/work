package com.wanmi.sbc.order.api.request.paytraderecord;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.bean.enums.TradeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 交易记录请求参数 */
@Data
@Schema
public class PayTradeRecordDeleteAndSaveRequest extends BaseRequest {

    private static final long serialVersionUID = -5366569924208689784L;

    /** 业务id(订单或退单号) */
    @Schema(description = "业务id(订单或退单号)")
    private String businessId;

    /** 申请价格 */
    @Schema(description = "申请价格")
    private BigDecimal applyPrice;

    /** 支付项(具体支付渠道)id */
    @Schema(description = "支付项(具体支付渠道)id")
    private Long channelItemId;

    /** 创建时间 */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /** 客户端ip */
    @Schema(description = "客户端ip")
    private String clientIp;

    /** 交易流水号 */
    @Schema(description = "交易流水号")
    private String tradeNo;

    @Schema(description = "交易类型")
    private TradeType tradeType;

    @Schema(description = "退款状态")
    private TradeStatus status;

    @Schema(description = "支付单号")
    private String payNo;
}
