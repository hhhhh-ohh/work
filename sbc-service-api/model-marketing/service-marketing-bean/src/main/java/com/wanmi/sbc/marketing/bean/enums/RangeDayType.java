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
 * @Date: Created In 10:21 AM 2018/9/12
 * @Description: 起止时间类型
 */
@ApiEnum
public enum RangeDayType {

    /**
     * 按起止时间
     */
    @ApiEnumProperty("0：按起止时间")
    RANGE_DAY(0),

    /**
     * 按N天有效
     */
    @ApiEnumProperty("1：按N天有效")
    DAYS(1);

    private Integer value;

    RangeDayType(Integer value){
        this.value = value;
    }

    private static Map<Integer, RangeDayType> dataMap = new HashMap<>();

    static {
        Arrays.asList(RangeDayType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static RangeDayType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum RangeDayTypeToIntegerConverter implements Converter<RangeDayType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(RangeDayType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToRangeDayTypeConverter implements Converter<Integer, RangeDayType> {
        INSTANCE;
        @Override
        public RangeDayType convert(Integer source) {
            for (RangeDayType item : RangeDayType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
