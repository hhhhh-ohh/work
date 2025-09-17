package com.wanmi.sbc.common.enums;

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
 * 是否默认,0否1是
 * Created by daiyitian on 2017/3/22.
 */
@ApiEnum
public enum DefaultFlag {
    @ApiEnumProperty("否")
    NO(0),
    @ApiEnumProperty("是")
    YES(1);

    private Integer value;

    DefaultFlag(Integer value){
        this.value = value;
    }

    private static Map<Integer, DefaultFlag> dataMap = new HashMap<>();

    static {
        Arrays.asList(DefaultFlag.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static DefaultFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum DefaultFlagToIntegerConverter implements Converter<DefaultFlag, Integer> {
        INSTANCE;
        @Override
        public Integer convert(DefaultFlag source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToDefaultFlagConverter implements Converter<Integer, DefaultFlag> {
        INSTANCE;
        @Override
        public DefaultFlag convert(Integer source) {
            for (DefaultFlag item : DefaultFlag.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
