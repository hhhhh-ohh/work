package com.wanmi.sbc.vas.bean.vo.channel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.AuditState;
import com.wanmi.sbc.common.enums.DeliverStatus;
import com.wanmi.sbc.common.enums.FlowState;
import com.wanmi.sbc.common.enums.PayState;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单总体状态
 * Created by jinwei on 19/03/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ChannelOrderStateVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    private AuditState auditState;

    /**
     * 流程状态
     */
    @Schema(description = "流程状态")
    private FlowState flowState;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态")
    private PayState payState;

    /**
     * 发货状态
     */
    @Schema(description = "发货状态")
    private DeliverStatus deliverStatus;

    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime modifyTime;

    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    @Schema(description = "付款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime payTime;
    /**
     * 发货时间
     */
    @Schema(description = "发货时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deliverTime;

    /**
     * 自动确认收货时间
     */
    @Schema(description = "自动确认收货时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime autoConfirmTime;

    /**
     * 进入支付页面的时间
     *
     * 暂时控制银联b2b支付，扫描订单超时支付的时候使用
     */
    @Schema(description = "进入支付页面的时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startPayTime;

    /**
     * 订单入账时间(由可退时间、处理完最后一笔退单时间决定)
     */
    @Schema(description = "订单入账时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime finalTime;

    /**
     * 作废原因，订单作废后设置
     */
    @Schema(description = "作废原因")
    private String obsoleteReason;


    /**
     * 定金支付开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime handSelStartTime;

    /**
     * 定金支付结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime handSelEndTime;

    /**
     * 尾款支付开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tailStartTime;

    /**
     * 尾款支付结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tailEndTime;

}
