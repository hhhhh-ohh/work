package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.enums.RecruitApplyType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>分销设置VO</p>
 *
 * @author baijz
 * @date 2019-02-19 10:08:02
 */
@Schema
@Data
public class DistributionSettingSimVO extends BasicResponse {

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
     * 申请条件 0：购买商品，1：邀请注册
     */
    @Schema(description = "申请条件")
    private RecruitApplyType applyType;

    /**
     * 是否开启申请入口  0：关闭  1：开启
     */
    @Schema(description = "申请入口是否开启")
    private DefaultFlag applyFlag;

    /**
     * 是否开启邀新
     */
    @Schema(description = "是否开启邀新")
    private DefaultFlag inviteOpenFlag;

    /**
     * 是否开启邀请奖励
     */
    @Schema(description = "是否开启邀新奖励")
    private DefaultFlag inviteFlag;

    /**
     * 邀新入口海报
     */
    @Schema(description = "邀新入口海报")
    private String inviteEnterImg;

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
     * 分销业绩规则说明
     */
    @Schema(description = "分销业绩规则说明")
    private String performanceDesc;

    /**
     * 分销员等级规则
     */
    @Schema(description = "分销员等级规则")
    private String distributorLevelDesc;
}