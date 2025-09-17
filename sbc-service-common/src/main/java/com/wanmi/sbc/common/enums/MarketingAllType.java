package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 营销活动类型
 */
@ApiEnum(dataType = "java.lang.String")
public enum MarketingAllType {

    /**
     * 所有
     */
    ALL("所有"),

    /**
     * 拼团
     */
    @ApiEnumProperty("1：拼团")
    GROUPON("拼团"),

    /**
     * 秒杀
     */
    @ApiEnumProperty("2：秒杀")
    FLASH_SALE("秒杀"),

    /**
     * 组合购
     */
    @ApiEnumProperty("3：组合购")
    SUIT("组合购"),

    /**
     * 预约购买
     */
    @ApiEnumProperty("4：预约购买")
    RESERVATION("预约购买"),

    /**
     * 预售
     */
    @ApiEnumProperty("5：全款预售")
    PRESALE("全款预售"),

    /**
     * 打包一口价
     */
    @ApiEnumProperty("6：打包一口价")
    BUYOUT_PRICE("打包一口价"),

    /**
     * 第二件半价
     */
    @ApiEnumProperty("7：第二件半价")
    DISCOUNT("第二件半价"),

    /**
     * 社交分销
     */
    @ApiEnumProperty("8：社交分销")
    DISTRIBUTION("社交分销"),

    /**
     * 企业购
     */
    @ApiEnumProperty("9：企业购")
    ENTERPRISE("企业购"),

    /**
     * 限时抢购
     */
    @ApiEnumProperty("10：限时抢购")
    LIMIT_BUY("限时抢购"),

    /**
     * 限售商品
     */
    @ApiEnumProperty("11：限售商品")
    RESTRICTED_SALES("限售商品"),

    /**
     * 满减
     */
    @ApiEnumProperty("12：满减优惠")
    FULL_REDUCTION("满减优惠"),

    /**
     * 满折
     */
    @ApiEnumProperty("13：满折优惠")
    FULL_DISCOUNT("满折优惠"),

    /**
     * 预售
     */
    @ApiEnumProperty("14：定金预售")
    EARNEST("定金预售"),

    /**
     * 满赠
     */
    @ApiEnumProperty("15：满赠优惠")
    FULL_GIFT("满赠优惠"),

    /**
     * 满返优惠
     */
    @ApiEnumProperty("16：满返优惠")
    FULL_RETURN("满返优惠"),

    /**
     * 付费会员折扣商品
     */
    @ApiEnumProperty("17：付费会员折扣商品")
    PAYING_MEMBER_DISCOUNT("付费会员折扣商品"),

    /**
     * 付费会员推荐商品
     */
    @ApiEnumProperty("18：付费会员推荐商品")
    PAYING_MEMBER_RECOMMEND("付费会员推荐商品"),

    @ApiEnumProperty("19：加价购优惠")
    PREFERENTIAL("加价购优惠");


    MarketingAllType(String desc) {
        this.desc = desc;
    }

    /**
     * 描述信息
     */
    private String desc;

    @JsonCreator
    public static MarketingAllType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    public String getDesc() {
        return desc;
    }

}
