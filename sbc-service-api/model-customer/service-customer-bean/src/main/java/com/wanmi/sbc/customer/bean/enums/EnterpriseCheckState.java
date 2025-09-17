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
 * 企业购审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
 */
@ApiEnum
public enum EnterpriseCheckState {
    @ApiEnumProperty("0：无状态")
    INIT(0),
    @ApiEnumProperty("1：待审核")
    WAIT_CHECK(1),
    @ApiEnumProperty("2：已审核")
    CHECKED(2),
    @ApiEnumProperty("3：审核未通过")
    NOT_PASS(3);

    private Integer value;

    EnterpriseCheckState(Integer value){
        this.value = value;
    }

    private static Map<Integer, EnterpriseCheckState> dataMap = new HashMap<>();

    static {
        Arrays.asList(EnterpriseCheckState.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static EnterpriseCheckState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum EnterpriseCheckStateToIntegerConverter implements Converter<EnterpriseCheckState, Integer> {
        INSTANCE;
        @Override
        public Integer convert(EnterpriseCheckState source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToEnterpriseCheckStateConverter implements Converter<Integer, EnterpriseCheckState> {
        INSTANCE;
        @Override
        public EnterpriseCheckState convert(Integer source) {
            for (EnterpriseCheckState item : EnterpriseCheckState.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
