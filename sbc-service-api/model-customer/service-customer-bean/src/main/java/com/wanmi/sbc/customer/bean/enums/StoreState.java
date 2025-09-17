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
 * 店铺状态 0、开启 1、关店
 * Created by CHENLI on 2017/11/2.
 */
@ApiEnum
public enum StoreState {

    @ApiEnumProperty("0、开启")
    OPENING(0),
    @ApiEnumProperty("1、关店")
    CLOSED(1);

    private Integer value;

    StoreState(Integer value){
        this.value = value;
    }

    private static Map<Integer, StoreState> dataMap = new HashMap<>();

    static {
        Arrays.asList(StoreState.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static StoreState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum StoreStateToIntegerConverter implements Converter<StoreState, Integer> {
        INSTANCE;
        @Override
        public Integer convert(StoreState source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToStoreStateConverter implements Converter<Integer, StoreState> {
        INSTANCE;
        @Override
        public StoreState convert(Integer source) {
            for (StoreState item : StoreState.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
