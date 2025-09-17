package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @Author: songhanlin
 * @Date: Created In 10:47 AM 2018/9/12
 * @Description: 优惠券类型
 * 0通用券 1店铺券
 */
@ApiEnum
public enum CouponType {

    /**
     * 通用券
     */
    @ApiEnumProperty("0：通用券")
    GENERAL_VOUCHERS(0),

    /**
     * 店铺券
     */
    @ApiEnumProperty("1：店铺券")
    STORE_VOUCHERS(1),

    /**
     * 门店券
     */
    @ApiEnumProperty("3：门店券")
    STOREFRONT_VOUCHER(3),

    /**
     * boss门店券
     */
    @ApiEnumProperty("4：boss门店券")
    BOSS_STOREFRONT_VOUCHER(4);

    private int type;

    CouponType(int type) {
        this.type = type;
    }

    @JsonCreator
    public static CouponType fromValue(int value) {
        for (CouponType couponType : values()) {
            if (couponType.toValue() == value) {
                return couponType;
            }
        }
        return null;
    }

    @JsonValue
    public int toValue() {
        return this.type;
    }
}
