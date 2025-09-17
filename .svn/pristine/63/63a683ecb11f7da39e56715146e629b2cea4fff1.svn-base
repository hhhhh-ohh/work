package com.wanmi.sbc.open.enums;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 2025年6月活动枚举
 */
public enum JuneActivityRewardLevelEnum {


    Level6(6,new BigDecimal("20"),new BigDecimal("299"),1,new BigDecimal("40")),
    Level5(5,new BigDecimal("15"),new BigDecimal("229"),1,new BigDecimal("30")),
    Level4(4,new BigDecimal("12"),new BigDecimal("179"),1,new BigDecimal("20")),
    Level3(3,new BigDecimal("10"),new BigDecimal("129"),1,new BigDecimal("15")),
    Level2(2,new BigDecimal("8"),new BigDecimal("99"),1,new BigDecimal("10")),
    Level1(1,new BigDecimal("8"),new BigDecimal("59"),1,new BigDecimal("0")),
    Level0(0,new BigDecimal("0"),new BigDecimal("0"),1,new BigDecimal("0"));


    //奖励等级
    private Integer level;

    //优惠券金额
    private BigDecimal couponAmount;

    //等级金额
    private BigDecimal rewardPrice;

    private Integer rewardPointRate;

    //优惠金额
    private BigDecimal discountPrice;

    JuneActivityRewardLevelEnum(Integer level, BigDecimal couponAmount
            ,BigDecimal rewardPrice,Integer rewardPointRate, BigDecimal discountPrice) {
        this.level = level;
        this.couponAmount = couponAmount;
        this.rewardPrice = rewardPrice;
        this.rewardPointRate = rewardPointRate;
        this.discountPrice = discountPrice;
    }

    public Integer getLevel() {
        return level;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public BigDecimal getRewardPrice() {
        return rewardPrice;
    }

    public Integer getRewardPointRate() {
        return rewardPointRate;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }



    public static JuneActivityRewardLevelEnum getByLevel(Integer level){
        for (JuneActivityRewardLevelEnum levelEnum : JuneActivityRewardLevelEnum.values()) {
            if (levelEnum.getLevel().equals(level)) {
                return levelEnum;
            }
        }
        return null;
    }

    public static JuneActivityRewardLevelEnum getByRewardPrice(BigDecimal price){
        for (JuneActivityRewardLevelEnum levelEnum : JuneActivityRewardLevelEnum.values()) {
            if (levelEnum.getRewardPrice().compareTo(price) <= 0) {
                return levelEnum;
            }
        }
        return null;
    }
}
