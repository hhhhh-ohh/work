package com.wanmi.sbc.goods.bean.enums;

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
 * 审核状态 0：待审核 1：已审核 2：审核失败 3：禁售中
 * Created by Daiyitian on 2017/4/13.
 */
@ApiEnum
public enum CheckStatus {
    @ApiEnumProperty("0：待审核")
    WAIT_CHECK(0),

    @ApiEnumProperty("1：已审核")
    CHECKED(1),

    @ApiEnumProperty("2：审核失败")
    NOT_PASS(2),

    @ApiEnumProperty("3：禁售中")
    FORBADE(3);

    private Integer value;

    CheckStatus(Integer value){
        this.value = value;
    }

    private static Map<Integer, CheckStatus> dataMap = new HashMap<>();

    static {
        Arrays.asList(CheckStatus.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static CheckStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum CheckStatusToIntegerConverter implements Converter<CheckStatus, Integer> {
        INSTANCE;
        @Override
        public Integer convert(CheckStatus source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToCheckStatusConverter implements Converter<Integer, CheckStatus> {
        INSTANCE;
        @Override
        public CheckStatus convert(Integer source) {
            for (CheckStatus item : CheckStatus.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
