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
 * 一个枚举两个含义(兼容业务)
 * 客户类型,0:平台客户/0:店铺关联的客户 ,1:商家客户/1:店铺发展的客户
 */
@ApiEnum
public enum CustomerType {
    @ApiEnumProperty("0:平台客户/0:店铺关联的客户")
    PLATFORM(0),
    @ApiEnumProperty("1:商家客户/1:店铺发展的客户")
    SUPPLIER(1),
    @ApiEnumProperty("2:直营门店客户")
    STOREFRONT(2);

    private Integer value;

    CustomerType(Integer value){
        this.value = value;
    }

    private static Map<Integer, CustomerType> dataMap = new HashMap<>();

    static {
        Arrays.asList(CustomerType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CustomerType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CustomerTypeToIntegerConverter implements Converter<CustomerType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CustomerType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCustomerTypeConverter implements Converter<Integer, CustomerType> {
        INSTANCE;
        @Override
        public CustomerType convert(Integer source) {
            for (CustomerType item : CustomerType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
