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
 * 审核状态 0：待审核 1：已审核 2：审核未通过
 * Created by CHENLI on 2017/4/13.
 */
@ApiEnum
public enum CheckState {
    @ApiEnumProperty("0：待审核")
    WAIT_CHECK(0),
    @ApiEnumProperty("1：已审核")
    CHECKED(1),
    @ApiEnumProperty("2：审核未通过")
    NOT_PASS(2);

    private Integer value;

    CheckState(Integer value){
        this.value = value;
    }

    private static Map<Integer, CheckState> dataMap = new HashMap<>();

    static {
        Arrays.asList(CheckState.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CheckState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CheckStateToIntegerConverter implements Converter<CheckState, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CheckState source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCheckStateConverter implements Converter<Integer, CheckState> {
        INSTANCE;
        @Override
        public CheckState convert(Integer source) {
            for (CheckState item : CheckState.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
