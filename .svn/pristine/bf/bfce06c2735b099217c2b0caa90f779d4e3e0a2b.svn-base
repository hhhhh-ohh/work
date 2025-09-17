package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.enums.CommissionPriorityType;
import com.wanmi.sbc.marketing.bean.enums.CommissionUnhookType;
import com.wanmi.sbc.marketing.bean.enums.DistributionLimitType;
import com.wanmi.sbc.marketing.bean.enums.RecruitApplyType;
import com.wanmi.sbc.marketing.bean.enums.RegisterLimitType;
import com.wanmi.sbc.marketing.bean.enums.RewardCashType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>分销设置VO</p>
 *
 * @author gaomuwei
 * @date 2019-02-19 10:08:02
 */
@Schema
@Data
public class DistributionSettingVO extends BasicResponse {

    private static final long serialVersionUID = 7307992054004432636L;

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Schema(description = "是否开启社交分销")
    private DefaultFlag openFlag;

    /**
     * 分销员名称
     */
    @Schema(description = "分销员名称")
    private String distributorName;

    /**
     * 是否开启分销小店 0：关闭，1：开启
     */
    @Schema(description = "是否开启分销小店")
    private DefaultFlag shopOpenFlag;

    /**
     * 小店名称
     */
    @Schema(description = "小店名称")
    private String shopName;

    /**
     * 店铺分享图片
     */
    @Schema(description = "店铺分享图片")
    private String shopShareImg;

    /**
     * 注册限制
     */
    @Schema(description = "注册限制")
    private RegisterLimitType registerLimitType;

    /**
     * 基础邀新奖励限制 0：不限，1：仅限有效邀新
     */
    @Schema(description = "基础邀新奖励限制")
    private DistributionLimitType baseLimitType;

    /**
     * 佣金返利优先级
     */
    @Schema(description = "佣金返利优先级")
    private CommissionPriorityType commissionPriorityType;

    /**
     * 是否开启申请入口 0：关闭，1：开启
     */
    @Schema(description = "是否开启申请入口")
    private DefaultFlag applyFlag;

    /**
     * 申请条件 0：购买商品，1：邀请注册
     */
    @Schema(description = "申请条件")
    private RecruitApplyType applyType;

    /**
     * 购买商品时招募入口海报
     */
    @Schema(description = "购买商品时招募入口海报")
    private String buyRecruitEnterImg;

    /**
     * 邀请注册时招募入口海报
     */
    @Schema(description = "邀请注册时招募入口海报")
    private String inviteRecruitEnterImg;

    /**
     * 邀请注册时招募落地页海报
     */
    @Schema(description = "邀请注册时招募落地页海报")
    private String inviteRecruitImg;

    /**
     * 招募海报
     */
    @Schema(description = "购买商品时招募落地页海报")
    private String recruitImg;

    /**
     * 邀新入口海报
     */
    @Schema(description = "邀新入口海报")
    private String inviteEnterImg;

    /**
     * 招募邀新转发图片
     */
    @Schema(description = "招募邀新转发图片")
    private String recruitShareImg;

    /**
     * 招募规则说明
     */
    @Schema(description = "招募规则说明")
    private String recruitDesc;

    /**
     * 招募规则说明-购买商品
     */
    @Schema(description = "招募规则说明-购买商品")
    private String recruitBuyDesc;

    /**
     * 邀请人数
     */
    @Schema(description = "邀请人数")
    private Integer inviteCount;

    /**
     * 限制条件 0：不限，1：仅限有效邀新
     */
    @Schema(description = "限制条件")
    private DistributionLimitType limitType;

    /**
     * 是否开启分销佣金 0：关闭，1：开启
     */
    @Schema(description = "是否开启分销佣金")
    private DefaultFlag commissionFlag;

    /**
     * 是否开启分销商品审核 0：关闭，1：开启
     */
    @Schema(description = "是否开启分销商品审核")
    private DefaultFlag goodsAuditFlag;

    /**
     * 是否开启邀新 0：关闭，1：开启
     */
    @Schema(description = "是否开启邀新")
    private DefaultFlag inviteOpenFlag;

    /**
     * 是否开启邀新奖励 0：关闭，1：开启
     */
    @Schema(description = "是否开启邀新奖励")
    private DefaultFlag inviteFlag;

    /**
     * 邀新专题页海报
     */
    @Schema(description = "邀新专题页海报")
    private String inviteImg;

    /**
     * 邀新转发图片
     */
    @Schema(description = "邀新转发图片")
    private String inviteShareImg;

    /**
     * 邀新奖励规则说明
     */
    @Schema(description = "邀新奖励规则说明")
    private String inviteDesc;

    /**
     * 邀新奖励限制 0：不限，1：仅限有效邀新
     */
    @Schema(description = "邀新奖励限制")
    private DistributionLimitType rewardLimitType;

    /**
     * 是否开启奖励现金 0：关闭，1：开启
     */
    @Schema(description = "是否开启奖励现金")
    private DefaultFlag rewardCashFlag;

    /**
     * 奖励上限类型 0：不限， 1：限人数
     */
    @Schema(description = "奖励上限类型")
    private RewardCashType rewardCashType;

    /**
     * 奖励现金上限(人数)
     */
    @Schema(description = "奖励现金上限(人数)")
    private Integer rewardCashCount;

    /**
     * 每位奖励金额
     */
    @Schema(description = "每位奖励金额")
    private BigDecimal rewardCash;

    /**
     * 是否开启奖励优惠券 0：关闭，1：开启
     */
    @Schema(description = "是否开启奖励优惠券")
    private DefaultFlag rewardCouponFlag;

    /**
     * 奖励优惠券上限(组数)
     */
    @Schema(description = "奖励优惠券上限(组数)")
    private Integer rewardCouponCount;

    /**
     * 分销业绩规则说明
     */
    @Schema(description = "分销业绩规则说明")
    private String performanceDesc;

    /**
     * 佣金提成脱钩
     */
    @Schema(description = "佣金提成脱钩")
    private CommissionUnhookType commissionUnhookType;

    /**
     * 分销员等级规则
     */
    @Schema(description = "分销员等级规则")
    private String distributorLevelDesc;

}