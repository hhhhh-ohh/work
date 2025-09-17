package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description: 邀新记录VO
 * @Autho qiaokang
 * @Date：2019-03-04 15:39:26
 */
@Schema
@Data
public class DistributionInviteNewVo extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 受邀人ID
     */
    @Schema(description = "受邀人ID")
    @NotNull
    private String invitedNewCustomerId;

    /**
     * 是否有效邀新，0：否，1：是
     */
    @Schema(description = "是否有效邀新，0：否，1：是")
    private InvalidFlag availableDistribution;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime registerTime;

    /**
     * 首次下单时间
     */
    @Schema(description = "首次下单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime firstOrderTime;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderCode;

    /**
     * 订单完成时间
     */
    @Schema(description = "订单完成时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderFinishTime;

    /**
     * 奖励是否入账，0：否，1：是
     */
    @Schema(description = "奖励是否入账，0：否，1：是")
    private InvalidFlag rewardRecorded;

    /**
     * 奖励入账时间
     */
    @Schema(description = "奖励入账时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime rewardRecordedTime;

    /**
     * 奖励金额(元)
     */
    @Schema(description = "奖励金额(元)")
    private BigDecimal rewardCash;

    /**
     * 后台配置的奖励优惠券id，多个以逗号分隔
     */
    @Schema(description = "后台配置的奖励优惠券id，多个以逗号分隔")
    private String settingCoupons;

    /**
     * 预计奖励金额(元)
     */
    @Schema(description = "后台配置的奖励金额")
    private BigDecimal settingAmount;

    /**
     * 邀请人id
     */
    @Schema(description = "邀请人id")
    private String requestCustomerId;

    /**
     * 是否分销员，0：否 1：是
     */
    @Schema(description = "是否分销员，0：否 1：是")
    private Integer distributor;

}
