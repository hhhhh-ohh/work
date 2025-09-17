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
 * 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
 *
 * @author chenli
 */
@ApiEnum
public enum GoodsPropertyEnterType {

    /** 选项 */
    @ApiEnumProperty("0：选项")
    CHOOSE(0),

    /** 文本 */
    @ApiEnumProperty("1: 文本")
    TEXT(1),

    /** 日期 */
    @ApiEnumProperty("2: 日期")
    DATE(2),

    /** 省市 */
    @ApiEnumProperty("3: 省市")
    PROVINCE(3),

    /** 国家或地区 */
    @ApiEnumProperty("4: 国家或地区")
    COUNTRY(4);

    private Integer value;

    GoodsPropertyEnterType(Integer value){
        this.value = value;
    }

    private static Map<Integer, GoodsPropertyEnterType> dataMap = new HashMap<>();

    static {
        Arrays.asList(GoodsPropertyEnterType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static GoodsPropertyEnterType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum GoodsPropertyEnterTypeToIntegerConverter implements Converter<GoodsPropertyEnterType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(GoodsPropertyEnterType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToGoodsPropertyEnterTypeConverter implements Converter<Integer, GoodsPropertyEnterType> {
        INSTANCE;
        @Override
        public GoodsPropertyEnterType convert(Integer source) {
            for (GoodsPropertyEnterType item : GoodsPropertyEnterType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
