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
 * 账号类型 0 b2b账号 1 s2b平台端账号 2 s2b商家端账号
 * Created by chenli on 2017/3/22.
 */
@ApiEnum
public enum AccountType {
    @ApiEnumProperty("b2b账号")
    b2bBoss(0),
    @ApiEnumProperty("s2b平台端账号")
    s2bBoss(1),
    @ApiEnumProperty("s2b商家端账号")
    s2bSupplier(2),
    @ApiEnumProperty("s2b供应商端账号")
    s2bProvider(3),
    @ApiEnumProperty("o2o门店端账号")
    O2O(4);

    private Integer value;

    AccountType(Integer value){
        this.value = value;
    }

    private static Map<Integer, AccountType> dataMap = new HashMap<>();

    static {
        Arrays.asList(AccountType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static AccountType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum AccountTypeToIntegerConverter implements Converter<AccountType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(AccountType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToAccountTypeConverter implements Converter<Integer, AccountType> {
        INSTANCE;
        @Override
        public AccountType convert(Integer source) {
            for (AccountType item : AccountType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
