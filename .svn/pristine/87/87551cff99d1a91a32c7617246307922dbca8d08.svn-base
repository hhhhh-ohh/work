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
 * @description 优惠券营销类型（0满减券 1满折券 2运费券）
 * @author malianfeng
 * @date 2022/9/27 14:31
 */
@ApiEnum
public enum CouponMarketingType {

    /**
     * 满减券
     */
    @ApiEnumProperty("0：满减券")
    REDUCTION_COUPON(0),

    /**
     * 满折券
     */
    @ApiEnumProperty("1：满折券")
    DISCOUNT_COUPON(1),

    /**
     * 运费券
     */
    @ApiEnumProperty("2：运费券")
    FREIGHT_COUPON(2);

    private Integer value;

    CouponMarketingType(Integer value){
        this.value = value;
    }

    private static Map<Integer, CouponMarketingType> dataMap = new HashMap<>();

    static {
        Arrays.asList(CouponMarketingType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CouponMarketingType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CouponMarketingTypeToIntegerConverter implements Converter<CouponMarketingType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CouponMarketingType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCouponMarketingTypeConverter implements Converter<Integer, CouponMarketingType> {
        INSTANCE;
        @Override
        public CouponMarketingType convert(Integer source) {
            for (CouponMarketingType item : CouponMarketingType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
