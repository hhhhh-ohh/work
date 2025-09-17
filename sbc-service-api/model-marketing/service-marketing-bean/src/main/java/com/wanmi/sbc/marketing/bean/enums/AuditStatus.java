package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 是否审核通过，0：待审核，1：审核通过，2：审核不通过
 */
public enum AuditStatus {
    @ApiEnumProperty("0：待审核")
    WAIT_CHECK(0),

    @ApiEnumProperty("1：已审核")
    CHECKED(1),

    @ApiEnumProperty("2：审核失败")
    NOT_PASS(2);

    private Integer value;

    AuditStatus(Integer value){
        this.value = value;
    }

    private static Map<Integer, AuditStatus> dataMap = new HashMap<>();

    static {
        Arrays.asList(AuditStatus.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static AuditStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public Integer toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum AuditStatusToIntegerConverter implements Converter<AuditStatus, Integer> {
        INSTANCE;
        @Override
        public Integer convert(AuditStatus source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToAuditStatusConverter implements Converter<Integer, AuditStatus> {
        INSTANCE;
        @Override
        public AuditStatus convert(Integer source) {
            for (AuditStatus item : AuditStatus.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }

}
