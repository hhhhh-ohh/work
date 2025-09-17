package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 布布型枚举,0否1是
 * Created by daiyitian on 2017/3/22.
 */
@ApiEnum
public enum BoolFlag {

    @ApiEnumProperty("否")
    NO(0),
    @ApiEnumProperty("是")
    YES(1);

    private Integer value;

    BoolFlag(Integer value){
        this.value = value;
    }

    private static Map<Integer, BoolFlag> dataMap = new HashMap<>();

    static {
        Arrays.asList(BoolFlag.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static BoolFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum BoolFlagToIntegerConverter implements Converter<BoolFlag, Integer> {
        INSTANCE;
        @Override
        public Integer convert(BoolFlag source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToBoolFlagConverter implements Converter<Integer, BoolFlag> {
        INSTANCE;
        @Override
        public BoolFlag convert(Integer source) {
            for (BoolFlag item : BoolFlag.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }

    public static BoolFlag fromValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return fromValue(Integer.parseInt(value));
    }
}
