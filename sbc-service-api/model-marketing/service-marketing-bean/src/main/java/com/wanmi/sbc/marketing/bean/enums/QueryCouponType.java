package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.checkerframework.checker.units.qual.C;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券
 * @author malianfeng
 * @date 2022/10/11 10:50
 */
@ApiEnum
public enum QueryCouponType {

    /**
     * 通用满减券
     */
    @ApiEnumProperty("0：通用满减券")
    GENERAL_REDUCTION(0),

    /**
     * 店铺满减券
     */
    @ApiEnumProperty("1：店铺满减券")
    STORE_REDUCTION(1),

    /**
     * 店铺满折券
     */
    @ApiEnumProperty("2：店铺满折券")
    STORE_DISCOUNT(2),

    /**
     * 店铺运费券
     */
    @ApiEnumProperty("3：店铺运费券")
    STORE_FREIGHT(3);

    private Integer value;

    QueryCouponType(Integer value){
        this.value = value;
    }

    private static Map<Integer, QueryCouponType> dataMap = new HashMap<>();

    static {
        Arrays.asList(QueryCouponType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static QueryCouponType fromValue(int value) {
        return values()[value];
    }

    public static QueryCouponType fromValue(CouponType couponType, CouponMarketingType couponMarketingType) {
        if (couponType == CouponType.GENERAL_VOUCHERS && couponMarketingType == CouponMarketingType.REDUCTION_COUPON) {
            return GENERAL_REDUCTION;
        } else if (couponType == CouponType.STORE_VOUCHERS && couponMarketingType == CouponMarketingType.REDUCTION_COUPON) {
            return STORE_REDUCTION;
        } else if (couponType == CouponType.STORE_VOUCHERS && couponMarketingType == CouponMarketingType.DISCOUNT_COUPON) {
            return STORE_DISCOUNT;
        } else if (couponType == CouponType.STORE_VOUCHERS && couponMarketingType == CouponMarketingType.FREIGHT_COUPON) {
            return STORE_FREIGHT;
        }
        return null;
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CouponTypeToIntegerConverter implements Converter<QueryCouponType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(QueryCouponType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCouponTypeConverter implements Converter<Integer, QueryCouponType> {
        INSTANCE;
        @Override
        public QueryCouponType convert(Integer source) {
            for (QueryCouponType item : QueryCouponType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
