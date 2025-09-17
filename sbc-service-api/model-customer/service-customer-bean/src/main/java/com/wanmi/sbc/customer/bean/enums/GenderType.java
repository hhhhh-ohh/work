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

/**
 * 性别，0：保密，1：男，2：女
 * @author xuyunpeng
 */
@ApiEnum
public enum GenderType {

    @ApiEnumProperty("保密")
    SECRET(0),
    @ApiEnumProperty("男")
    MALE(1),
    @ApiEnumProperty("女")
    FEMALE(2);

    private Integer value;

    GenderType(Integer value){
        this.value = value;
    }

    private static Map<Integer, GenderType> dataMap = new HashMap<>();

    static {
        Arrays.asList(GenderType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static GenderType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum GenderTypeToIntegerConverter implements Converter<GenderType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(GenderType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToGenderTypeConverter implements Converter<Integer, GenderType> {
        INSTANCE;
        @Override
        public GenderType convert(Integer source) {
            for (GenderType item : GenderType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
