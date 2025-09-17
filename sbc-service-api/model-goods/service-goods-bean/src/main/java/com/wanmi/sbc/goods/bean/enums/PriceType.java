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

/**
 * 数据类型
 * 0：spu数据 1sku数据
 * Created by daiyitian on 2017/3/22.
 */
@ApiEnum
public enum PriceType {

    @ApiEnumProperty("0：spu数据")
    SPU(0),

    @ApiEnumProperty("1: sku数据")
    SKU(1);

    private Integer value;

    PriceType(Integer value){
        this.value = value;
    }

    private static Map<Integer, PriceType> dataMap = new HashMap<>();

    static {
        Arrays.asList(PriceType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static PriceType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum PriceTypeToIntegerConverter implements Converter<PriceType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(PriceType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToPriceTypeConverter implements Converter<Integer, PriceType> {
        INSTANCE;
        @Override
        public PriceType convert(Integer source) {
            for (PriceType item : PriceType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
