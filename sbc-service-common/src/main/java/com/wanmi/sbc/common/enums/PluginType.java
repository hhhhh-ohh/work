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
 * 账号类型 0 b2b账号 1 s2b平台端账号 2 s2b商家端账号
 * Created by chenli on 2017/3/22.
 */
@ApiEnum
public enum PluginType {
    @ApiEnumProperty("正常类型商品")
    NORMAL(0),
    @ApiEnumProperty("跨境商品")
    CROSS_BORDER(1),
    @ApiEnumProperty("O2O商品")
    O2O(2);

    private Integer value;

    PluginType(Integer value){
        this.value = value;
    }

    private static Map<Integer, PluginType> dataMap = new HashMap<>();

    static {
        Arrays.asList(PluginType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static PluginType fromValue(Integer value) {
        if(value == null) {
            return null;
        }
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum PluginTypeToIntegerConverter implements Converter<PluginType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(PluginType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToPluginTypeConverter implements Converter<Integer, PluginType> {
        INSTANCE;
        @Override
        public PluginType convert(Integer source) {
            for (PluginType item : PluginType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
