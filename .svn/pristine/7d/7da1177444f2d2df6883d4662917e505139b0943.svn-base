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
 * @author 黄昭
 * @className LogOutStatus
 * @description 注销状态 0:正常 1:注销中 2:已注销
 * @date 2022/3/28 11:14
 **/
@ApiEnum
public enum LogOutStatus {

    @ApiEnumProperty("0：正常")
    NORMAL(0),
    @ApiEnumProperty("1：注销中")
    LOGGING_OFF(1),
    @ApiEnumProperty("2：已注销")
    LOGGED_OUT(2);

    private Integer value;

    LogOutStatus(Integer value){
        this.value = value;
    }

    private static Map<Integer, LogOutStatus> dataMap = new HashMap<>();

    static {
        Arrays.asList(LogOutStatus.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static LogOutStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CustomerTypeToIntegerConverter implements Converter<LogOutStatus, Integer> {
        INSTANCE;
        @Override
        public Integer convert(LogOutStatus source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCustomerTypeConverter implements Converter<Integer, LogOutStatus> {
        INSTANCE;
        @Override
        public LogOutStatus convert(Integer source) {
            for (LogOutStatus item : LogOutStatus.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}