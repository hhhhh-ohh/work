package com.wanmi.sbc.customer.api.request.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 更新邀新记录-有效邀新
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionInviteNewModifyRegisterRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 购买人编号
     */
    @Schema(description = "购买人编号")
    private String orderBuyCustomerId;


    /**
     * 订单完成时间
     */
    @Schema(description = "订单完成时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderFinishTime;

    /**
     * 订单下单时间
     */
    @Schema(description = "订单下单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderCreateTime;

    /**
     *  奖励入账时间
     */
    @Schema(description = "奖励入账时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderCode;

    /**
     * 是否开启奖励现金开关
     */
    @Schema(description = "是否开启奖励现金开关")
    private DefaultFlag rewardCashFlag;

    /**
     * 奖励上限设置
     */
    @Schema(description = "奖励上限设置")
    private DefaultFlag rewardCashType;

    /**
     * 奖励现金上限(人数)
     */
    @Schema(description = "奖励现金上限(人数)")
    private Long rewardCashCount;

    /**
     * 是否开启奖励优惠券
     */
    @Schema(description = "是否开启奖励优惠券")
    private DefaultFlag rewardCouponFlag;

    /**
     * 奖励优惠券上限(组数)
     */
    @Schema(description = "奖励优惠券上限(组数)")
    private Integer rewardCouponCount;

    /**
     * 优惠券面值总额
     */
    @Schema(description = "优惠券面值总额")
    private BigDecimal denominationSum;

    /**
     * 终端
     */
    @Schema(description = "终端")
    private TerminalSource terminalSource;


}
