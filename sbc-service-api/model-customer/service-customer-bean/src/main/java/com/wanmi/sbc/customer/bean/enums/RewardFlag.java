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
 * 入账类型（0：现金 1：优惠券）
 */
@ApiEnum
public enum RewardFlag {
    @ApiEnumProperty("入账类型 0:现金")
    CASH(0),
    @ApiEnumProperty("入账类型 1:优惠券")
    COUPON(1);

    private Integer value;

    RewardFlag(Integer value){
        this.value = value;
    }

    private static Map<Integer, RewardFlag> dataMap = new HashMap<>();

    static {
        Arrays.asList(RewardFlag.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static RewardFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum RewardFlagToIntegerConverter implements Converter<RewardFlag, Integer> {
        INSTANCE;
        @Override
        public Integer convert(RewardFlag source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToRewardFlagConverter implements Converter<Integer, RewardFlag> {
        INSTANCE;
        @Override
        public RewardFlag convert(Integer source) {
            for (RewardFlag item : RewardFlag.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
