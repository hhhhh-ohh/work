package com.wanmi.ares.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 营销活动类型
 */
public enum MarketingTypeBase {
    /**
     * 满减
     */
    REDUCTION("满减优惠"),

    /**
     * 满折
     */
    DISCOUNT("满折优惠"),

    /**
     * 满赠
     */
    GIFT("满赠优惠"),

    /**
     * 一口价优惠
     */
    BUYOUT_PRICE("一口价优惠"),

    /**
     * 第二件半价优惠活动
     */
    HALF_PRICE_SECOND_PIECE("第二件半价优惠活动"),

    FLASH_SALE("秒杀"),

    SUITS("组合套餐"),

    @ApiEnumProperty("7：满折优惠")
    RETURN("满返优惠"),

    @ApiEnumProperty("8：加价购")
    PREFERENTIAL("加价购");

    MarketingTypeBase(String desc) {
        this.desc = desc;
    }

    /**
     * 描述信息
     */
    private String desc;

    @JsonCreator
    public static MarketingTypeBase fromValue(int value) {
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
