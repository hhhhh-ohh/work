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
public class PayTradeRecordRequest extends BaseRequest {

    private static final long serialVersionUID = 2201927986481315179L;

    @Schema(description = "主键")
    private String id;

    /** 业务id(订单或退单号) */
    @Schema(description = "业务id(订单或退单号)")
    private String businessId;

    /** 支付渠道方返回的支付或退款对象id */
    @Schema(description = "支付渠道方返回的支付或退款对象id")
    private String chargeId;

    /** 申请价格 */
    @Schema(description = "申请价格")
    private BigDecimal applyPrice;

    /** 实际成功交易价格 */
    @Schema(description = "实际成功交易价格")
    private BigDecimal practicalPrice;

    /** 支付项(具体支付渠道)id */
    @Schema(description = "支付项(具体支付渠道)id")
    private Long channelItemId;

    /** 创建时间 */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /** 交易完成时间 */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "交易完成时间")
    private LocalDateTime finishTime;

    /** 回调时间 */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "回调时间")
    private LocalDateTime callbackTime;

    /** 客户端ip */
    @Schema(description = "客户端ip")
    private String clientIp;

    /** 交易流水号 */
    @Schema(description = "交易流水号")
    private String tradeNo;

    /** 业务结果 */
    @Schema(description = "业务结果")
    private String result_code;

    @Schema(description = "交易类型")
    private TradeType tradeType;

    @Schema(description = "退款状态")
    private TradeStatus status;

    @Schema(description = "支付单号")
    private String payNo;
}
