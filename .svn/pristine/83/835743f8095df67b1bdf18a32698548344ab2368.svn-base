package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.enums.FailReasonFlag;
import com.wanmi.sbc.customer.bean.enums.RewardFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by feitingting on 2019/2/21.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributionInviteNewForPageVO extends BasicResponse {

    /**
     * 邀新记录表主键
     */
    @Schema(description = "邀新记录表主键ID")
    private String recordId;

    /**
     * 受邀人ID
     */
    @Schema(description = "受邀人ID")
    private String  invitedNewCustomerId;

    /**
     * 邀请人ID
     */
    @Schema(description = "邀请人ID")
    private String  requestCustomerId;

    /**
     * 受邀人名称
     */
    @Schema(description = "受邀人名称")
    private String  invitedNewCustomerName;

    /**
     * 受邀人账号
     */
    @Schema(description = "受邀人账号")
    private String  invitedNewCustomerAccount;

    /**
     * 受邀人账号
     */
    @Schema(description = "受邀人头像")
    private String  invitedNewCustomerHeadImg;

    /**
     * 邀请人名称
     */
    @Schema(description = "邀请人名称")
    private String  requestCustomerName;

    /**
     * 邀请人账号
     */
    @Schema(description = "邀请人账号")
    private String  requestCustomerAccount;

    /**
     * 邀请人账号
     */
    @Schema(description = "邀请人头像")
    private String  requestCustomerHeadImg;

    /**
     * 是否有效邀新 0：否 1：是
     */
    @Schema(description = "是否有效邀新")
    private InvalidFlag availableDistribution;

    /**
     * 是否分销员 0：否  1：是
     */
    @Schema(description = "是否分销员", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer  distributor;

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
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "首次下单时间")
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
     * 奖励是否入账 0:否 1:是
     */
    @Schema(description = "奖励是否入账")
    private InvalidFlag rewardRecorded;

    /**
     * 奖励入账时间
     */
    @Schema(description = "奖励入账时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime rewardRecordedTime;

    /**
     * 奖励金额
     */
    @Schema(description = "奖励金额")
    private BigDecimal rewardCash;

    /**
     * 后台配置的奖励优惠券id，多个以逗号分隔
     */
    @Schema(description = "后台配置的奖励优惠券id，多个以逗号分隔")
    private String settingCoupons;

    /**
     * 预计奖励金额
     */
    @Schema(description = "后台配置的奖励金额")
    private BigDecimal settingAmount;

    /**
     * 未入账原因(0:非有效邀新，1：奖励达到上限)
     */
    @Schema(description = "未入账原因(0:非有效邀新，1：奖励达到上限)")
    private FailReasonFlag failReasonFlag;

    /**
     * 入账类型（0：现金 1：优惠券）
     */
    @Schema(description = "入账类型")
    private RewardFlag  rewardFlag ;

    /**
     * 入账优惠券
     */
    @Schema(description = "入账优惠券")
    private String  rewardCoupon ;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus invitedNewCustomerLogOutStatus;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus requestCustomerLogOutStatus;
}
