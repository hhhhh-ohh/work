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
 * @Date: Created In 10:28 AM 2018/9/12
 * @Description: 购满类型
 */
@ApiEnum
public enum FullBuyType {

    /**
     * 无门槛
     */
    @ApiEnumProperty("0：无门槛")
    NO_THRESHOLD(0),

    /**
     * 满N元可使用
     */
    @ApiEnumProperty("1：满N元可使用")
    FULL_MONEY(1);

    private Integer value;

    FullBuyType(Integer value){
        this.value = value;
    }

    private static Map<Integer, FullBuyType> dataMap = new HashMap<>();

    static {
        Arrays.asList(FullBuyType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static FullBuyType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum FullBuyTypeToIntegerConverter implements Converter<FullBuyType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(FullBuyType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToFullBuyTypeConverter implements Converter<Integer, FullBuyType> {
        INSTANCE;
        @Override
        public FullBuyType convert(Integer source) {
            for (FullBuyType item : FullBuyType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
