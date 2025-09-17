package com.wanmi.ares.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 营销类型
 */
public enum MarketingType {
    // 优惠券
    COUPON,
    // 拼团
    GROUPON,
    // 秒杀
    FLASH_SALE,
    // 满系
    REDUCTION_DISCOUNT_GIFT,
    // 打包一口价
    BUYOUT_PRICE,
    // 第二件半价
    HALF_PRICE_SECOND_PIECE,
    // 组合购
    SUITS,
    // 预约
    APPOINTMENT_SALE,
    // 全款预售
    FULL_MONEY_BOOKING_SALE,
    // 订金预售
    DEPOSIT_BOOKING_SALE,
    // 预售
    BOOKING_SALE,
    // 商品砍价
    GOODS_BARGAIN,
    // 加价购
    PREFERENTIAL;

    @JsonCreator
    public MarketingType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
