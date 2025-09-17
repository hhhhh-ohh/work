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

/** 0未删除 1已删除 Created by zhangjin on 2017/3/22. */
@ApiEnum
public enum DeleteFlag {
    @ApiEnumProperty("0:否")
    NO(0),
    @ApiEnumProperty("1:是")
    YES(1);

    private Integer value;

    DeleteFlag(Integer value){
        this.value = value;
    }

    private static Map<Integer, DeleteFlag> dataMap = new HashMap<>();

    static {
        Arrays.asList(DeleteFlag.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static DeleteFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum DeleteFlagToIntegerConverter implements Converter<DeleteFlag, Integer> {
        INSTANCE;
        @Override
        public Integer convert(DeleteFlag source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToDeleteFlagConverter implements Converter<Integer, DeleteFlag> {
        INSTANCE;
        @Override
        public DeleteFlag convert(Integer source) {
            for (DeleteFlag item : DeleteFlag.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
