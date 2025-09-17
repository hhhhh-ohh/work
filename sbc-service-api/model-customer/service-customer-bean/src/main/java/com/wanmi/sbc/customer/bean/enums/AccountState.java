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

/** Created by zhangjin on 2017/4/18. */
@ApiEnum
public enum AccountState {
    @ApiEnumProperty("启用")
    ENABLE(0),
    @ApiEnumProperty("禁用")
    DISABLE(1),
    @ApiEnumProperty("离职")
    DIMISSION(2);

    private Integer value;

    AccountState(Integer value) {
        this.value = value;
    }

    private static Map<Integer, AccountState> dataMap = new HashMap<>();

    static {
        Arrays.asList(AccountState.values()).forEach(t -> dataMap.put(t.getValue(), t));
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static AccountState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum AccountStateToIntegerConverter implements Converter<AccountState, Integer> {
        INSTANCE;

        @Override
        public Integer convert(AccountState source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToAccountStateConverter implements Converter<Integer, AccountState> {
        INSTANCE;

        @Override
        public AccountState convert(Integer source) {
            for (AccountState item : AccountState.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
