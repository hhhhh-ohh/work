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
 * Created by hht on 2017/12/6.
 */
@ApiEnum
public enum SettleStatus {

    @ApiEnumProperty("未结算")
    NOT_SETTLED(0),

    @ApiEnumProperty("已结算")
    SETTLED(1),

    @ApiEnumProperty("暂不处理")
    SETTLE_LATER(2);

    private Integer value;

    SettleStatus(Integer value){
        this.value = value;
    }

    private static Map<Integer, SettleStatus> dataMap = new HashMap<>();

    static {
        Arrays.asList(SettleStatus.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static SettleStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum SettleStatusToIntegerConverter implements Converter<SettleStatus, Integer> {
        INSTANCE;
        @Override
        public Integer convert(SettleStatus source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToSettleStatusConverter implements Converter<Integer, SettleStatus> {
        INSTANCE;
        @Override
        public SettleStatus convert(Integer source) {
            for (SettleStatus item : SettleStatus.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }

}
