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
 *
 * @Author: zhangwenchang
 * @Date: Created In 上午9:49 2022/1/6
 * @Description: 平台类型
 */
@ApiEnum
public enum PlatformType {

    /**
     * BOSS
     */
    @ApiEnumProperty("BOSS")
    BOSS(0),

    /**
     * 商家
     */
    @ApiEnumProperty("STORE")
    STORE(1);

    private Integer value;

    PlatformType(Integer value){
        this.value = value;
    }

    private static Map<Integer, PlatformType> dataMap = new HashMap<>();

    static {
        Arrays.asList(PlatformType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static PlatformType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum PlatformTypeToIntegerConverter implements Converter<PlatformType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(PlatformType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToPlatformTypeConverter implements Converter<Integer, PlatformType> {
        INSTANCE;
        @Override
        public PlatformType convert(Integer source) {
            for (PlatformType item : PlatformType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }

}
