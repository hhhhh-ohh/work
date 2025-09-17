package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hht on 2017/12/6.
 */
@ApiEnum
public enum StoreType {

    @ApiEnumProperty("0:供应商")
    PROVIDER(0),

    @ApiEnumProperty("1:商家")
    SUPPLIER(1),

    @ApiEnumProperty("2:门店")
    O2O(2),

    @ApiEnumProperty("3:跨境商家")
    CROSS_BORDER(3);

    private Integer value;

    StoreType(Integer value){
        this.value = value;
    }

    private static Map<Integer, StoreType> dataMap = new HashMap<>();

    static {
        Arrays.asList(StoreType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static StoreType fromValue(Integer value) {
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
    public enum StoreTypeToIntegerConverter implements Converter<StoreType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(StoreType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToStoreTypeConverter implements Converter<Integer, StoreType> {
        INSTANCE;
        @Override
        public StoreType convert(Integer source) {
            for (StoreType item : StoreType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }

    public static List<Integer> getStoreTypeWithOutO2o(){
        return Lists.newArrayList(PROVIDER.toValue(),SUPPLIER.toValue(),CROSS_BORDER.toValue());
    }
}
