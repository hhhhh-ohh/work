package com.wanmi.ares.enums;


import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @ClassName CouponActivityType
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/25 10:15
 * @Version 1.0
 **/
public enum CouponActivityType {

    /**
     * 全场赠券
     */
    @Schema(description = "0：全场赠券")
    ALL_COUPONS(0,"全场赠券"),

    /**
     * 指定赠券
     */
    @Schema(description = "1：指定赠券")
    SPECIFY_COUPON(1,"指定赠券"),

    /**
     * 进店赠券
     */
    @Schema(description = "2：进店赠券")
    STORE_COUPONS(2,"进店赠券"),

    /**
     * 注册赠券
     */
    @Schema(description = "3：注册赠券")
    REGISTERED_COUPON(3,"注册赠券"),

    /**
     * 权益赠券
     */
    @Schema(description = "4：权益赠券")
    RIGHTS_COUPON(4,"权益赠券"),

    /**
     * 分销邀新赠券
     */
    @Schema(description = "5：分销邀新赠券")
    DISTRIBUTE_COUPON(5,"分销邀新赠券"),

    /**
     * 积分兑换券
     */
    @Schema(description = "6: 积分兑换券")
    POINTS_COUPON(6,"积分兑换券"),

    /**
     * 企业会员注册赠券
     */
    @Schema(description = "7: 企业会员注册赠券")
    ENTERPRISE_REGISTERED_COUPON(7,"企业会员注册赠券"),

    /**
     * 抽奖赠券
     */
    @Schema(description = "8: 抽奖赠券")
    DRAW_COUPON(8, "抽奖赠券"),

    /**
     * 新人专享
     */
    @ApiEnumProperty("9: 新人专享")
    NEW_CUSTOMER_COUPON(9, "新人专享");


    private int code;
    private String name;

    CouponActivityType(int code,String name) {
        this.code=code;
        this.name=name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}