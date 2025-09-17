package com.wanmi.sbc.marketing.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.marketing.bean.dto.DistributionRewardCouponDTO;
import com.wanmi.sbc.marketing.bean.enums.RewardCashType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午9:44 2019/2/23
 * @Description: 分销奖励设置请求对象
 */
@Schema
@Data
public class DistributionRewardSettingSaveRequest extends BaseRequest {

    private static final long serialVersionUID = 8680590841288850016L;

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Schema(description = "是否开启社交分销")
    @NotNull
    private DefaultFlag openFlag;

    /**
     * 是否开启分销佣金 0：关闭，1：开启
     */
    @Schema(description = "是否开启分销佣金")
    @NotNull
    private DefaultFlag commissionFlag;

    /**
     * 是否开启邀新 0：关闭，1：开启
     */
    @Schema(description = "是否开启邀新")
    @NotNull
    private DefaultFlag inviteOpenFlag;

    /**
     * 是否开启邀新奖励 0：关闭，1：开启
     */
    @Schema(description = "是否开启邀新奖励")
    @NotNull
    private DefaultFlag inviteFlag;

    /**
     * 邀新入口海报
     */
    @Schema(description = "邀新入口海报")
    private String inviteEnterImg;

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
     * 奖励的优惠券
     */
    @Schema(description = "奖励的优惠券")
    private List<DistributionRewardCouponDTO> chooseCoupons;


    @Override
    public void checkParam() {

        // 开启邀新开关switch，勾选
        if (inviteOpenFlag == DefaultFlag.YES) {

            // 邀新入口海报，非空
            if (StringUtils.isBlank(inviteEnterImg)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            // 邀新落地页海报，非空
            if (StringUtils.isBlank(inviteImg)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            //  邀新转发图片，非空
            if (StringUtils.isBlank(inviteShareImg)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            // 邀新奖励开关switch，勾选
            if (inviteFlag == DefaultFlag.YES) {

                // 奖励现金checkbox，非空
                if (Objects.isNull(rewardCashFlag)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                // 奖励现金checkbox，勾选
                if (rewardCashFlag == DefaultFlag.YES) {
                    // 每位奖励金额，非空
                    if (Objects.isNull(rewardCash)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    // 每位奖励金额，范围1-99999999
                    if (!ValidateUtil.isInRange(rewardCash, 1, 99999999)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    // 奖励上限类型radio，非空
                    if (Objects.isNull(rewardCashType)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    // 奖励上限类型radio，选中不限
                    if (rewardCashType == RewardCashType.LIMITED) {
                        // 奖励现金上限(人数)，非空
                        if (Objects.isNull(rewardCashCount)) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                        }
                        // 奖励现金上限(人数)，范围1-99999
                        if (!ValidateUtil.isInRange(rewardCashCount, 1, 99999)) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                        }
                    }
                }

                // 奖励优惠券checkbox，非空
                if (Objects.isNull(rewardCouponFlag)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                // 奖励优惠券checkbox，勾选
                if (rewardCouponFlag == DefaultFlag.YES) {
                    // 奖励优惠券上限(组数)，非空
                    if (Objects.isNull(rewardCouponCount)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    // 奖励优惠券上限(组数)，范围1-999999999
                    if (!ValidateUtil.isInRange(rewardCouponCount, 1, 999999999)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    // 奖励的优惠券，非空
                    if (CollectionUtils.isEmpty(chooseCoupons)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    // 奖励的优惠券，最多选1-10张
                    if (!ValidateUtil.isInRange(chooseCoupons.size(), 1, 10)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    // 奖励的优惠券，元素非空，每组赠送张数1-10张
                    for (DistributionRewardCouponDTO item : chooseCoupons) {
                        if (Objects.isNull(item) || Objects.isNull(item.getCouponId()) || Objects.isNull(item.getCount())) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                        }
                        if (!ValidateUtil.isInRange(item.getCount(), 1, 10)) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                        }
                    }
                }
            }
        }
    }
}
