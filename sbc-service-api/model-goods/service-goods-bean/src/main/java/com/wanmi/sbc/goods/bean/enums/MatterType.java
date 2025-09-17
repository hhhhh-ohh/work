package com.wanmi.sbc.goods.bean.enums;

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

@ApiEnum
public enum MatterType {
    @ApiEnumProperty("0: 商品素材")
    GOODS(0),
    @ApiEnumProperty("1: 营销素材")
    MARKETING(1);

    private Integer value;

    MatterType(Integer value){
        this.value = value;
    }

    private static Map<Integer, MatterType> dataMap = new HashMap<>();

    static {
        Arrays.asList(MatterType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static MatterType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum MatterTypeToIntegerConverter implements Converter<MatterType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(MatterType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToMatterTypeConverter implements Converter<Integer, MatterType> {
        INSTANCE;
        @Override
        public MatterType convert(Integer source) {
            for (MatterType item : MatterType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
