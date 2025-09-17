package com.wanmi.sbc.customer.bean.enums;

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

/** 增专资质是否作废 0：否 1：是 Created by CHENLI on 2017/4/13. */
@ApiEnum(dataType = "java.lang.String")
public enum InvalidFlag {
    /** 否 0 */
    @ApiEnumProperty("增专资质是否作废 0：否")
    NO("否"),
    /** 是 1 */
    @ApiEnumProperty("增专资质是否作废 1：是")
    YES("是");

    private String value;

    InvalidFlag(String value) {
        this.value = value;
    }

    private static Map<String, InvalidFlag> dataMap = new HashMap<>();

    static {
        Arrays.asList(InvalidFlag.values()).forEach(t -> dataMap.put(t.getValue(), t));
    }

    public String getValue() {
        return this.value;
    }

    @WritingConverter
    public enum InvalidFlagToIntegerConverter implements Converter<InvalidFlag, Integer> {
        INSTANCE;
        @Override
        public Integer convert(InvalidFlag source) {
            return source.ordinal();
        }
    }

    @ReadingConverter
    public enum IntegerToInvalidFlagConverter implements Converter<Integer, InvalidFlag> {
        INSTANCE;
        @Override
        public InvalidFlag convert(Integer source) {
            for (InvalidFlag item : InvalidFlag.values()) {
                if (item.ordinal() == source) {
                    return item;
                }
            }
            return null;
        }
    }

    @JsonCreator
    public static InvalidFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
