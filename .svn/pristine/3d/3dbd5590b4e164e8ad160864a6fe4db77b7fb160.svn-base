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
 * @Author : baijz
 * @Date : 2019/2/26 15 18
 * @Description :
 */
@ApiEnum(dataType = "java.lang.String")
public enum CommissionReceived {

    /**
     * 分销未入账
     */
    @ApiEnumProperty("0:未入账")
    UNRECEIVE("佣金未入账"),

    /**
     * 分销已入账
     */
    @ApiEnumProperty("1:分销已入账")
    RECEIVED("佣金已入账");

    private String value;

    CommissionReceived(String value) {
        this.value = value;
    }

    private static Map<String, CommissionReceived> dataMap = new HashMap<>();

    static {
        Arrays.asList(CommissionReceived.values()).forEach(t -> dataMap.put(t.getValue(), t));
    }

    public String getValue() {
        return this.value;
    }

    @WritingConverter
    public enum CommissionReceivedToIntegerConverter implements Converter<CommissionReceived, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CommissionReceived source) {
            return source.ordinal();
        }
    }

    @ReadingConverter
    public enum IntegerToCommissionReceivedConverter implements Converter<Integer, CommissionReceived> {
        INSTANCE;
        @Override
        public CommissionReceived convert(Integer source) {
            for (CommissionReceived item : CommissionReceived.values()) {
                if (item.ordinal() == source) {
                    return item;
                }
            }
            return null;
        }
    }

    @JsonCreator
    public CommissionReceived fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
