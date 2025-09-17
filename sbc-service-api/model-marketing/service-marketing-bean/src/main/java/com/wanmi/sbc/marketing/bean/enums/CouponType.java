package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

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
     * 平台门店券
     */
    @ApiEnumProperty("4：平台门店券")
    BOSS_STOREFRONT_VOUCHER(4);

    private Integer value;

    CouponType(Integer value){
        this.value = value;
    }

    private static Map<Integer, CouponType> dataMap = new HashMap<>();

    static {
        Arrays.asList(CouponType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CouponType fromValue(int value) {
        return dataMap.get(value);
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CouponTypeToIntegerConverter implements Converter<CouponType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CouponType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCouponTypeConverter implements Converter<Integer, CouponType> {
        INSTANCE;
        @Override
        public CouponType convert(Integer source) {
            for (CouponType item : CouponType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }

}
