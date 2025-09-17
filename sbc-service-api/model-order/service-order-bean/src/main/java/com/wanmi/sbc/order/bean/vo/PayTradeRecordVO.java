package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.TradeType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.empower.bean.vo.PayChannelItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wur
 * @description 支付订单详情
 * @date 2021/4/16 19:44
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayTradeRecordVO extends BasicResponse {
    private static final long serialVersionUID = 5742265513858256132L;

    @Schema(description = "支付交易记录id")
    private String id;

    /**
     * 业务id(订单或退单号)
     */
    @Schema(description = "业务id(订单或退单号)")
    private String businessId;

    /**
     * 支付渠道方返回的支付或退款对象id
     */
    @Schema(description = "支付渠道方返回的支付或退款对象id")
    private String chargeId;

    /**
     * 申请价格
     */
    @Schema(description = "申请价格")
    private BigDecimal applyPrice;

    /**
     * 实际成功交易价格
     */
    @Schema(description = "实际成功交易价格")
    private BigDecimal practicalPrice;

    /**
     * 交易类型
     */
    @Schema(description = "交易类型")
    private TradeType tradeType;

    /**
     * 状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败
     */
    @Schema(description = "状态")
    private TradeStatus status;

    /**
     * 支付项(具体支付渠道)id
     */
    @Schema(description = "支付项(具体支付渠道)id")
    private Long channelItemId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 交易完成时间
     */
    @Schema(description = "交易完成时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime finishTime;

    /**
     * 回调时间
     */
    @Schema(description = "回调时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime callbackTime;

    /**
     * 客户端ip
     */
    @Schema(description = "客户端ip")
    private String clientIp;

    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;

    @Schema(description = "支付渠道信息")
    private PayChannelItemVO payChannelItemVo;
}
