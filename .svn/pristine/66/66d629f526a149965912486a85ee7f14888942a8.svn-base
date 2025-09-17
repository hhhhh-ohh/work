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
 * @description 优惠券优惠方式（0减免 1包邮）
 * 目前 仅当营销类型为 运费券 时，此字段有效
 * @author malianfeng
 * @date 2022/9/27 14:31
 */
@ApiEnum
public enum CouponDiscountMode {

    /**
     * 减免
     */
    @ApiEnumProperty("0：减免")
    REDUCTION(0),

    /**
     * 包邮
     */
    @ApiEnumProperty("1：包邮")
    FREIGHT_FREE(1);

    private Integer value;

    CouponDiscountMode(Integer value){
        this.value = value;
    }

    private static Map<Integer, CouponDiscountMode> dataMap = new HashMap<>();

    static {
        Arrays.asList(CouponDiscountMode.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CouponDiscountMode fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CouponDiscountModeToIntegerConverter implements Converter<CouponDiscountMode, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CouponDiscountMode source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCouponDiscountModeConverter implements Converter<Integer, CouponDiscountMode> {
        INSTANCE;
        @Override
        public CouponDiscountMode convert(Integer source) {
            for (CouponDiscountMode item : CouponDiscountMode.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
