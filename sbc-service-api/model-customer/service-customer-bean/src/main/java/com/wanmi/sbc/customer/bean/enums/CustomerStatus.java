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
 * 账号状态 0：启用中  1：禁用中
 * Created by CHENLI on 2017/4/13.
 */
@ApiEnum
public enum CustomerStatus {
    @ApiEnumProperty("0：启用中")
    ENABLE(0),
    @ApiEnumProperty("1：禁用中")
    DISABLE(1);

    private Integer value;

    CustomerStatus(Integer value){
        this.value = value;
    }

    private static Map<Integer, CustomerStatus> dataMap = new HashMap<>();

    static {
        Arrays.asList(CustomerStatus.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CustomerStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CustomerStatusToIntegerConverter implements Converter<CustomerStatus, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CustomerStatus source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCustomerStatusConverter implements Converter<Integer, CustomerStatus> {
        INSTANCE;
        @Override
        public CustomerStatus convert(Integer source) {
            for (CustomerStatus item : CustomerStatus.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
