package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: songhanlin
 * @Date: Created In 11:17 AM 2018/9/12
 * @Description: 优惠券活动类型，0全场赠券，1指定赠券，2进店赠券，3注册赠券
 */
@ApiEnum
public enum CouponActivityType {

    /**
     * 全场赠券
     */
    @ApiEnumProperty("0：全场赠券")
    ALL_COUPONS(0),

    /**
     * 指定赠券
     */
    @ApiEnumProperty("1：指定赠券")
    SPECIFY_COUPON(1),

    /**
     * 进店赠券
     */
    @ApiEnumProperty("2：进店赠券")
    STORE_COUPONS(2),

    /**
     * 注册赠券
     */
    @ApiEnumProperty("3：注册赠券")
    REGISTERED_COUPON(3),

    /**
     * 权益赠券
     */
    @ApiEnumProperty("4：权益赠券")
    RIGHTS_COUPON(4),

    /**
     * 分销邀新赠券
     */
    @ApiEnumProperty("5：分销邀新赠券")
    DISTRIBUTE_COUPON(5),

    /**
     * 积分兑换券
     */
    @ApiEnumProperty("6: 积分兑换券")
    POINTS_COUPON(6),

    /**
     * 企业会员注册赠券
     */
    @ApiEnumProperty("7: 企业会员注册赠券")
    ENTERPRISE_REGISTERED_COUPON(7),

    /**
     * 抽奖赠券
     */
    @ApiEnumProperty("8: 抽奖赠券")
    DRAW_COUPON(8),

    /**
     * 新人赠券
     */
    @ApiEnumProperty("9: 新人赠券")
    NEW_CUSTOMER_COUPON(9);

    private Integer value;

    CouponActivityType(Integer value){
        this.value = value;
    }

    private static Map<Integer, CouponActivityType> dataMap = new HashMap<>();

    static {
        Arrays.asList(CouponActivityType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CouponActivityType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CouponActivityTypeToIntegerConverter implements Converter<CouponActivityType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CouponActivityType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCouponActivityTypeConverter implements Converter<Integer, CouponActivityType> {
        INSTANCE;
        @Override
        public CouponActivityType convert(Integer source) {
            for (CouponActivityType item : CouponActivityType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }


}
