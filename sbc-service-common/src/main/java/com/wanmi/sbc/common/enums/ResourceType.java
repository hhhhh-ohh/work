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
 * Created by yang on 2019/11/10.
 */
@ApiEnum
public enum ResourceType {
    @ApiEnumProperty("图片")
    IMAGE(0),
    @ApiEnumProperty("视频")
    VIDEO(1),
    @ApiEnumProperty("excel")
    EXCEL(2);

    private Integer value;

    ResourceType(Integer value){
        this.value = value;
    }

    private static Map<Integer, ResourceType> dataMap = new HashMap<>();

    static {
        Arrays.asList(ResourceType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static ResourceType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum ResourceTypeToIntegerConverter implements Converter<ResourceType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(ResourceType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToResourceTypeConverter implements Converter<Integer, ResourceType> {
        INSTANCE;
        @Override
        public ResourceType convert(Integer source) {
            for (ResourceType item : ResourceType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
